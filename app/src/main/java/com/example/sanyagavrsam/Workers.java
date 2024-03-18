package com.example.sanyagavrsam;

public class Workers {

    private long ID;
    private String Name;
    private int Year;

    Workers(long ID, String Name, int Year){ this.ID = ID;
        this.Name = Name; this.Year = Year;
    }
    public long getId() { return ID;
    }
    public String getName() { return Name;
    }

    public void setName(String Name) { this.Name = Name;
    }

    public int getYear() { return Year;
    }

    public void setYear(int Year) { this.Year = Year;
    }

    @Override
    public String toString() {
        return this.Name + " : " + this.Year;
    }
}
