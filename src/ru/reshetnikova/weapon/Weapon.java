package ru.reshetnikova.weapon;

abstract class Weapon {
    private int ammo;

    public Weapon(int ammo) {
        if (ammo<0) throw new RuntimeException("Количество патронов не может быть отрицательным");
        this.ammo = ammo;
    }
    abstract void shoot();
    public int ammo () {
        return ammo;
    }
    public boolean getAmmo () {
        if (ammo==0) {
            return false;
        }
        ammo--;
        return true;
    }
    public  int load (int ammo) {
        if (ammo<0) {
            throw new RuntimeException("Количество патронов не может быть отрицательным");
        }
        int tmp=ammo;
        this.ammo = ammo;
        return tmp;
    }
}
