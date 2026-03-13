package org.example;

public class Tarea {

    private int id;
    private String descripcion;
    private String fecha;
    private String prioridad;
    private boolean completada;

    public Tarea() {
    }

    public Tarea(int id, String descripcion, String fecha, String prioridad) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.prioridad = prioridad;
        this.completada = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void completar() {
        this.completada = true;
    }
}