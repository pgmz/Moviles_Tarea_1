package com.iteso.tarea1;

public class Alumno {

    private String name;
    private String phone;
    private String scholarship;
    private String gender;
    private String favbook;
    private Boolean doesSports;

    Alumno(String name, String phone, String scholarship, String gender, String favbook, Boolean doesSports){
        this.name = name;
        this.phone = phone;
        this.scholarship = scholarship;
        this.gender = gender;
        this.favbook = favbook;
        this.doesSports = doesSports;
    }

    public String toString() {
        return "Nombre: " + this.name
                + "\nTeléfono: " + this.phone
                + "\nEscolaridad: " + this.scholarship
                + "\nGénero: " + this.gender
                + "\nLibro Favorito: " + this.favbook
                + "\nPractica Deporte: " + (this.doesSports?"Sí":"No");
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getScholarship() {
        return scholarship;
    }

    public String getGender() {
        return gender;
    }

    public String getFavbook() {
        return favbook;
    }

    public Boolean getDoesSports() {
        return doesSports;
    }
}
