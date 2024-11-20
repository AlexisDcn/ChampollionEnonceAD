package champollion;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Intervention {
    private Salle salle;
    @Setter
    private UE ue;
    private TypeIntervention typeInter;
    private Date debut;
    private int duree;
    @Setter
    private boolean annule;
    private int heureDebut;

    public Intervention(Date debut, int duree, boolean annule, int heureDebut) {
        this.debut = debut;
        this.duree = duree;
        this.annule = annule;
        this.heureDebut = heureDebut;
    }

    public Intervention(Salle s, UE java, Enseignant untel, TypeIntervention typeIntervention, Date date, int duree) {
        this.salle = s;
        this.ue = java;
        this.typeInter = typeIntervention;
        this.debut = date;
        this.duree = duree;
    }


    @Override
    public String toString() {
        return "Intervention : \n" + "debut = " + debut + ", duree = " + duree + ", annule = " + annule + ", heureDebut = " + heureDebut;
    }


}
