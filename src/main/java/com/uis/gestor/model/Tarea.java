package com.uis.gestor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@ToString
@Table(name = "tarea")
public class Tarea implements Serializable {

    private static final long serialVersionUID = 9158390070467800870L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_proyecto", nullable = false)
    private Long idProyecto;

    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_registro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "nombre", nullable = false)
    private String nombre;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", insertable = false, updatable = false)
    private Proyecto proyecto;

}
