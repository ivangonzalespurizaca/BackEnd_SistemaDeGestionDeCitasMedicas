-- ==========================================================
-- SCRIPT DE CONSOLIDACIÓN Y EXPANSIÓN - CLÍNICA SANTA ROSA
-- FECHA DE CARGA: 28 DE DICIEMBRE, 2025 (06:50 AM)
-- ==========================================================

USE DB_Hospital;

-- 1. ESPECIALIDADES
INSERT INTO Especialidad (Nombre_Especialidad) VALUES 
('Medicina General'), ('Pediatría'), ('Ginecología'), ('Cardiología'), ('Odontología');

-- 2. USUARIOS
INSERT INTO Usuario (Username, Contrasenia, Nombres, Apellidos, DNI, Telefono, Rol) VALUES
('admin1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Luis Alberto', 'Ramírez Torres', '72134567', '955623125', 'ADMINISTRADOR'),
('recep1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'María Fernanda', 'Gonzales Huamán', '71234568', '988552541', 'RECEPCIONISTA'),
('caje1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Carlos Enrique', 'Pérez Salazar', '73214569', '954125412', 'CAJERO'),
('medi1', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Pedro Carlos', 'Sanchez Valverde', '75214521', '955623125', 'MEDICO'),
('medi2', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Anthony Renato', 'Rojas Salazar', '98745215', '988552541', 'MEDICO'),
('medi3', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Sofia Lorena', 'Diaz Pereda', '87412541', '954125412', 'MEDICO'),
('medi4', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Rodrigo Gustavo', 'Rosales Palma', '77874547', '999985421', 'MEDICO'),
('medi5', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Carolina Fernanda', 'Talledo Salas', '73323215', '963252352', 'MEDICO'),
('medi6', '$2a$10$pehyH/xsPAjfSYDgzqvX5uZMKEHtT/z6Ikslr7x9Ej3UUZnq5gr3G', 'Vanessa Esperanza', 'Cardenas Palomino', '71114524', '952365236', 'MEDICO');

-- 3. MÉDICOS
INSERT INTO Medico (ID_Usuario, Nro_Colegiatura, Especialidad_ID) VALUES 
(4, 'COL-1001', 1), (5, 'COL-1002', 2), (6, 'COL-1003', 3),
(7, 'COL-1004', 4), (8, 'COL-1005', 5), (9, 'COL-1006', 4);

-- 4. PACIENTES
INSERT INTO Paciente (Nombres, Apellidos, DNI, Fecha_nacimiento, Telefono) VALUES 
('Javier', 'Mendoza Castillo', '74851236', '1990-05-12', '987321654'),
('Lucía', 'Ramírez Paredes', '71589423', '1985-09-27', '956478213'),
('Carlos', 'Torres Huamán', '73214589', '2000-01-08', '945612378'),
('Ana', 'Riva Agüero', '76543210', '1992-03-15', '912345678'),
('Roberto', 'Lozano Vega', '72345678', '1988-11-20', '923456789'),
('Elena', 'Casas Rojas', '78901234', '1995-07-04', '934567890'),
('Miguel', 'Soto Mayor', '70123456', '1982-12-30', '945678901'),
('Sofia', 'Bermudez Luna', '75678901', '1998-02-14', '956789012'),
('Daniel', 'Paz Soldán', '74321098', '1975-06-22', '967890123'),
('Valentina', 'Flores Valdivia', '73210987', '2005-10-10', '978901234'),
('Jorge', 'Guerra Tello', '72109876', '1968-08-18', '989012345'),
('Isabel', 'Cruzado Ruiz', '71098765', '1993-04-12', '990123456'),
('Ricardo', 'Palma Ferrand', '70987654', '1980-01-25', '910234567'),
('Carmen', 'Salcedo Diaz', '79876543', '1987-12-05', '920345678'),
('Fernando', 'Suarez Gil', '78765432', '1991-09-17', '930456789');

-- 5. HORARIOS Y SLOTS (Configuración para Domingo 28 y futuros)
INSERT INTO Horarios_Atencion (Id_Medico, Dia_Semana, Horario_Entrada, Horario_Salida) VALUES
(1, 'DOMINGO', '09:00:00', '13:00:00'), (2, 'DOMINGO', '08:00:00', '12:00:00'),
(3, 'DOMINGO', '14:00:00', '18:00:00'), (4, 'DOMINGO', '08:00:00', '12:00:00'),
(5, 'DOMINGO', '09:00:00', '13:00:00'), (6, 'DOMINGO', '14:00:00', '18:00:00');

CALL GenerarSlots();

-- 6. CITAS (Lógica temporal corregida al 28/12/2025 06:50 AM)
INSERT INTO Cita (ID_Cita, ID_Medico, ID_Paciente, ID_Usuario, Fecha, Hora, Motivo, Estado) VALUES
-- Pasadas (Ya atendidas)
(1, 1, 1, 2, '2025-12-18', '09:00:00', 'Control inicial', 'ATENDIDO'),
(2, 2, 3, 2, '2025-12-18', '08:00:00', 'Dolor de columna', 'ATENDIDO'),
(3, 1, 1, 2, '2025-12-22', '09:00:00', 'Consulta general', 'ATENDIDO'),
(11, 1, 4, 2, '2025-12-27', '09:00:00', 'Control presión', 'ATENDIDO'),
(12, 2, 7, 2, '2025-12-27', '11:00:00', 'Crecimiento y desarrollo', 'ATENDIDO'),
-- Hoy Domingo 28/12 (Próximas, por lo tanto NO atendidas aún)
(22, 1, 8, 2, '2025-12-28', '10:00:00', 'Control post-operatorio', 'CONFIRMADO'),
(23, 2, 9, 2, '2025-12-28', '09:00:00', 'Chequeo pediátrico', 'CONFIRMADO'),
(24, 3, 10, 2, '2025-12-28', '15:00:00', 'Consulta ginecológica', 'CONFIRMADO'),
(25, 4, 11, 2, '2025-12-28', '11:00:00', 'Evaluación cardiaca', 'CONFIRMADO'),
(26, 5, 12, 2, '2025-12-28', '12:00:00', 'Limpieza dental', 'CONFIRMADO'),
(27, 1, 13, 2, '2025-12-28', '12:00:00', 'Revisión de síntomas', 'PENDIENTE'),
(28, 2, 14, 2, '2025-12-28', '08:00:00', 'Vacunación refuerzo', 'PENDIENTE'),
(29, 3, 15, 2, '2025-12-28', '17:00:00', 'Control de rutina', 'PENDIENTE');

-- 7. COMPROBANTES DE PAGO (Solo para citas ATENDIDAS o CONFIRMADAS)
INSERT INTO Comprobante_Pago (ID_Cita, ID_Usuario, Nombres_Pagador, Apellidos_Pagador, DNI_Pagador, Contacto_Pagador, Monto, Metodo_Pago) VALUES
(1, 3, 'Javier', 'Mendoza', '74851236', '987321654', 80.00, 'EFECTIVO'),
(2, 3, 'Carlos', 'Torres', '73214589', '945612378', 80.00, 'EFECTIVO'),
(3, 3, 'Javier', 'Mendoza', '74851236', '987321654', 80.00, 'EFECTIVO'),
(11, 3, 'Ana', 'Riva Agüero', '76543210', '912345678', 100.00, 'EFECTIVO'),
(12, 3, 'Miguel', 'Soto Mayor', '70123456', '945678901', 90.00, 'TRANSFERENCIA'),
-- Pagos realizados hoy para citas de hoy (Por eso están CONFIRMADAS)
(22, 3, 'Jorge', 'Guerra Tello', '72109876', '989012345', 100.00, 'EFECTIVO'),
(23, 3, 'Isabel', 'Cruzado Ruiz', '71098765', '990123456', 90.00, 'TARJETA'),
(24, 3, 'Carlos', 'Torres Huamán', '73214589', '945612378', 120.00, 'TRANSFERENCIA'),
(25, 3, 'Ricardo', 'Palma Ferrand', '70987654', '910234567', 150.00, 'TARJETA'),
(26, 3, 'Carmen', 'Salcedo Diaz', '79876543', '920345678', 85.00, 'EFECTIVO');

-- 8. HISTORIAL MÉDICO (Solo para citas pasadas con estado ATENDIDO)
INSERT INTO Historial_Medico (ID_Cita, ID_Usuario, Diagnostico, Tratamiento, Notas_Adicionales, Peso, Presion_Arterial) VALUES
(1, 4, 'Salud general óptima.', 'Sin tratamiento.', 'Chequeo preventivo.', 70.00, '120/80'),
(2, 5, 'Lumbalgia mecánica.', 'Reposo y analgésicos.', 'Evitar cargar peso.', 75.00, '130/85'),
(3, 4, 'Gripe común.', 'Antigripales y líquidos.', 'Descanso médico 2 días.', 70.00, '115/75'),
(11, 4, 'Hipertensión leve.', 'Enalapril 10mg.', 'Dieta hiposódica.', 82.00, '140/90'),
(12, 5, 'Crecimiento dentro de los parámetros.', 'Multivitamínicos.', 'Continuar dieta balanceada.', 15.00, '90/60');

-- 9. SINCRONIZACIÓN FINAL DE SLOTS
SET SQL_SAFE_UPDATES = 0;
UPDATE Slot_Horario s
INNER JOIN Cita c ON s.ID_Medico = c.ID_Medico AND s.Fecha = c.Fecha AND s.Hora_Inicio = c.Hora
SET s.Estado = 'RESERVADO', s.ID_Cita = c.ID_Cita;
SET SQL_SAFE_UPDATES = 1;