package com.cibertec.app.service;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.cibertec.app.dto.ComprobanteDePagoResponseDTO;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CdpPdfService {

    public ByteArrayInputStream generarHistorialPagosPdf(List<ComprobanteDePagoResponseDTO> historial) {
        // Usamos formato horizontal para que quepan todos los datos del pagador y la cita
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- ESTILOS ---
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(0, 51, 102));
            Font fontSub = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
            Font fontCuerpo = FontFactory.getFont(FontFactory.HELVETICA, 8);
            Font fontResumen = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new Color(0, 51, 102));

            // --- ENCABEZADO ---
            Paragraph clinica = new Paragraph("CLÍNICA SANTA ROSA", fontTitulo);
            document.add(clinica);
            
            Paragraph reporte = new Paragraph("HISTORIAL DE COMPROBANTES DE PAGO", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY));
            document.add(reporte);

            String fechaGen = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            Paragraph info = new Paragraph("Generado el: " + fechaGen + " | Reporte de transacciones monetarias.", fontSub);
            info.setSpacingAfter(15);
            document.add(info);

            // --- TABLA (10 Columnas) ---
            PdfPTable table = new PdfPTable(10);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{0.8f, 1.2f, 2f, 3.5f, 1.5f, 3.5f, 1.2f, 2f, 1.5f, 1.5f});

            // Cabeceras
            String[] headers = {"N°", "ID Pago", "Fecha Emisión", "Pagador", "DNI Pag.", "Paciente", "ID Cita", "Est. Cita", "Estado", "Monto"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
                cell.setBackgroundColor(new Color(0, 102, 204));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Datos y variables para el resumen
            int item = 1;
            BigDecimal sumaTotal = BigDecimal.ZERO;

            for (ComprobanteDePagoResponseDTO pago : historial) {
                Color bg = (item % 2 == 0) ? new Color(245, 250, 255) : Color.WHITE;
                
                addCell(table, String.valueOf(item), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, "#" + pago.getIdComprobante(), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, pago.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, pago.getNombresPagador() + " " + pago.getApellidosPagador(), fontCuerpo, bg, Element.ALIGN_LEFT);
                addCell(table, pago.getDniPagador(), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, pago.getPacienteNombreCompleto(), fontCuerpo, bg, Element.ALIGN_LEFT);
                addCell(table, String.valueOf(pago.getIdCita()), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, pago.getEstadoCita(), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, pago.getEstado().name(), fontCuerpo, bg, Element.ALIGN_CENTER);
                addCell(table, "S/ " + pago.getMonto().toString(), fontCuerpo, bg, Element.ALIGN_RIGHT);

                sumaTotal = sumaTotal.add(pago.getMonto());
                item++;
            }
            document.add(table);

            // --- RESUMEN DE RECAUDACIÓN ---
            PdfPTable resumenTable = new PdfPTable(2);
            resumenTable.setWidthPercentage(30);
            resumenTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            resumenTable.setSpacingBefore(15);

            PdfPCell c1 = new PdfPCell(new Phrase("TOTAL RECAUDADO:", fontResumen));
            c1.setBorder(Rectangle.NO_BORDER);
            resumenTable.addCell(c1);

            PdfPCell c2 = new PdfPCell(new Phrase("S/ " + sumaTotal.setScale(2).toString(), fontResumen));
            c2.setBorder(Rectangle.NO_BORDER);
            c2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            resumenTable.addCell(c2);

            document.add(resumenTable);
            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addCell(PdfPTable table, String text, Font font, Color bg, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bg);
        cell.setHorizontalAlignment(align);
        cell.setPadding(4);
        cell.setBorderColor(new Color(230, 230, 230));
        table.addCell(cell);
    }
}