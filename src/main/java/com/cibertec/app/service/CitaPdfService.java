package com.cibertec.app.service;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.cibertec.app.dto.CitaResponseDTO;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CitaPdfService {

    public ByteArrayInputStream generarReporteCitas(List<CitaResponseDTO> citas) {
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 30, 30);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // --- CONFIGURACIÓN DE FUENTES ---
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new Color(0, 51, 102));
            Font fontSub = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY);
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
            Font fontCuerpo = FontFactory.getFont(FontFactory.HELVETICA, 9);
            Font fontFooter = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(0, 51, 102));

            // --- ENCABEZADO: Título y Metadatos ---
            Paragraph titulo = new Paragraph("CLÍNICA SANTA ROSA", fontTitulo);
            titulo.setAlignment(Element.ALIGN_LEFT);
            document.add(titulo);

            Paragraph subtitulo = new Paragraph("Reporte Administrativo de Citas Programadas", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY));
            document.add(subtitulo);

            String fechaGen = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            Paragraph meta = new Paragraph("Fecha de generación: " + fechaGen + "\nDescripción: Listado detallado de pacientes y asignación médica del turno actual.", fontSub);
            meta.setSpacingAfter(20);
            document.add(meta);

            // --- TABLA DE DATOS (9 Columnas: Se añade el N°) ---
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);
            // Ajuste de anchos: El N° y el ID son delgados, el Paciente y Médico son anchos
            table.setWidths(new float[]{0.8f, 1.2f, 2f, 1.5f, 2f, 3f, 2.5f, 2f, 4f}); 

            // Cabeceras
            String[] headers = {"N°", "ID", "Fecha", "Hora", "Estado", "Médico", "Especialidad", "DNI Pac.", "Paciente"};
            for (String headerTitle : headers) {
                PdfPCell header = new PdfPCell(new Phrase(headerTitle, fontHeader));
                header.setBackgroundColor(new Color(0, 102, 204)); // Azul institucional
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                header.setPadding(6);
                header.setBorderColor(Color.WHITE);
                table.addCell(header);
            }

            // Llenado de filas con Rayado de Cebra
            int contador = 1;
            for (CitaResponseDTO cita : citas) {
                Color backgroundColor = (contador % 2 == 0) ? new Color(240, 248, 255) : Color.WHITE;
                
                agregarCelda(table, String.valueOf(contador), fontCuerpo, backgroundColor, Element.ALIGN_CENTER);
                agregarCelda(table, "#" + cita.getIdCita(), fontCuerpo, backgroundColor, Element.ALIGN_CENTER);
                agregarCelda(table, cita.getFecha().toString(), fontCuerpo, backgroundColor, Element.ALIGN_CENTER);
                agregarCelda(table, cita.getHora().toString(), fontCuerpo, backgroundColor, Element.ALIGN_CENTER);
                agregarCelda(table, cita.getEstado().name(), fontCuerpo, backgroundColor, Element.ALIGN_CENTER);
                agregarCelda(table, cita.getNombreCompletoMedico(), fontCuerpo, backgroundColor, Element.ALIGN_LEFT);
                agregarCelda(table, cita.getEspecialidadNombre(), fontCuerpo, backgroundColor, Element.ALIGN_LEFT);
                agregarCelda(table, cita.getDniPaciente(), fontCuerpo, backgroundColor, Element.ALIGN_CENTER);
                agregarCelda(table, cita.getNombreCompletoPaciente(), fontCuerpo, backgroundColor, Element.ALIGN_LEFT);
                
                contador++;
            }

            document.add(table);

            // --- SECCIÓN DE RESUMEN (FINAL) ---
            Paragraph line = new Paragraph("\n------------------------------------------------------------------------------------------------------------------------------------", fontSub);
            document.add(line);

            Paragraph resumen = new Paragraph("RESUMEN DEL REPORTE", fontFooter);
            resumen.setSpacingBefore(10);
            document.add(resumen);

            Paragraph total = new Paragraph("Total de registros encontrados: " + (contador - 1), fontCuerpo);
            document.add(total);

            document.close();

        } catch (DocumentException ex) {
            ex.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    // Método auxiliar para celdas consistentes
    private void agregarCelda(PdfPTable table, String texto, Font font, Color color, int aligment) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBackgroundColor(color);
        cell.setPadding(5);
        cell.setHorizontalAlignment(aligment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(new Color(220, 220, 220));
        table.addCell(cell);
    }
}