package champollion;
import lombok.Getter;

import java.util.Date;

@Getter
public class Intervention {
    private Salle salle;
    private UE ue;
    private TypeIntervention typeInter;
    private Date debut;
    private int duree;
    private boolean annule;
    private int heureDebut;

    public Intervention(Date debut, int duree, boolean annule, int heureDebut) {
        this.debut = debut;
        this.duree = duree;
        this.annule = annule;
        this.heureDebut = heureDebut;
    }

    @Override
    public String toString() {
        return "Intervention : \n" + "debut = " + debut + ", duree = " + duree + ", annule = " + annule + ", heureDebut = " + heureDebut;
    }


}
