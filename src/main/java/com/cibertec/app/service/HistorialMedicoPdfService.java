package com.cibertec.app.service;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.cibertec.app.dto.HistorialMedicoResponseDTO;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class HistorialMedicoPdfService {

    public ByteArrayInputStream generarReporteHistorial(List<HistorialMedicoResponseDTO> historiales) {
        // Formato horizontal para dar prioridad al texto del diagnóstico
        Document document = new Document(PageSize.A4.rotate(), 20, 20, 30, 30);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- ESTILOS DE FUENTE ---
            Font fontClinica = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(0, 74, 173));
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.DARK_GRAY);
            Font fontSub = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY);
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);
            Font fontCuerpo = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.BLACK);
            Font fontDiagnostico = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, new Color(40, 40, 40));

            // --- ENCABEZADO ---
            Paragraph clinica = new Paragraph("CLÍNICA SANTA ROSA", fontClinica);
            document.add(clinica);

            Paragraph titulo = new Paragraph("REPORTE CONSOLIDADO DE HISTORIALES MÉDICOS", fontTitulo);
            document.add(titulo);

            String fechaGen = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Paragraph info = new Paragraph("Documento de uso estrictamente profesional | Generado el: " + fechaGen, fontSub);
            info.setSpacingAfter(15);
            document.add(info);

            // --- TABLA (9 Columnas) ---
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            // El Diagnóstico (última columna) tiene el mayor peso (6.0)
            table.setWidths(new float[]{0.8f, 1.2f, 1.2f, 2f, 2f, 4f, 2.5f, 2.5f, 6f}); 

            // Cabeceras
            String[] headers = {"N°", "ID Hist.", "ID Cita", "Fecha", "DNI", "Paciente", "Médico Responsable", "Especialidad", "Diagnóstico Clínico"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
                cell.setBackgroundColor(new Color(0, 123, 255)); // Azul médico
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(6);
                table.addCell(cell);
            }

            // Datos
            int nro = 1;
            for (HistorialMedicoResponseDTO h : historiales) {
                Color rowColor = (nro % 2 == 0) ? new Color(242, 247, 255) : Color.WHITE;

                addCell(table, String.valueOf(nro), fontCuerpo, rowColor, Element.ALIGN_CENTER);
                addCell(table, "#" + h.getIdHistorial(), fontCuerpo, rowColor, Element.ALIGN_CENTER);
                addCell(table, "#" + h.getIdCita(), fontCuerpo, rowColor, Element.ALIGN_CENTER);
                addCell(table, h.getFecha().toString(), fontCuerpo, rowColor, Element.ALIGN_CENTER);
                addCell(table, h.getPacienteDni(), fontCuerpo, rowColor, Element.ALIGN_CENTER);
                addCell(table, h.getPacienteNombreCompleto(), fontCuerpo, rowColor, Element.ALIGN_LEFT);
                addCell(table, h.getNombreMedicoResponsable(), fontCuerpo, rowColor, Element.ALIGN_LEFT);
                addCell(table, h.getEspecialidadNombre(), fontCuerpo, rowColor, Element.ALIGN_LEFT);
                
                // Celda especial para diagnóstico (cursiva y alineación justificada)
                PdfPCell diagCell = new PdfPCell(new Phrase(h.getDiagnostico(), fontDiagnostico));
                diagCell.setBackgroundColor(rowColor);
                diagCell.setPadding(5);
                diagCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                table.addCell(diagCell);

                nro++;
            }

            document.add(table);

            // --- NOTA AL PIE ---
            Paragraph footer = new Paragraph("\nFin del reporte: Se contabilizaron " + (nro - 1) + " registros médicos.", fontSub);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

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
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderColor(new Color(220, 230, 240));
        table.addCell(cell);
    }
}