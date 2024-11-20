package champollion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Un enseignant est caractérisé par les informations suivantes : son nom, son adresse email, et son service prévu,
 * et son emploi du temps.
 */
public class Enseignant extends Personne {

    private ArrayList<ServicePrevu> lesServicesPrevus;
    ServicePrevu service;
    private ArrayList<Intervention> lesInterventions;

    private HashMap<UE, ServicePrevu> lesEnseignements;

    public Enseignant(String nom, String email) {
        super(nom, email);
        lesServicesPrevus = new ArrayList<>();
        lesInterventions = new ArrayList<>();
        lesEnseignements = new HashMap<>();
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        int equivalentTD = 0;
        for (ServicePrevu service : lesServicesPrevus) {
            equivalentTD += (int) service.getVolumeCM() * 1.5;
            equivalentTD += (int) service.getVolumeTD();
            equivalentTD += (int) service.getVolumeTP() * 0.75;
        }
        return equivalentTD;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        int equivalentTD = 0;
        for (ServicePrevu service : lesServicesPrevus) {
            if (service.getUe() == ue) {
                equivalentTD += (int) service.getVolumeCM() * 1.5;
                equivalentTD += (int) service.getVolumeTD();
                equivalentTD += (int) service.getVolumeTP() * 0.75;
            }
        }
        return equivalentTD;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magistral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        if (volumeCM < 0 || volumeTD < 0 || volumeTP < 0) {
            throw new IllegalArgumentException("Les valeurs des volumes d'heures doivent être positives ou nulles");
        }

        //ServicePrevu service = lesEnseignements.get(ue);

        if (service == null) {
            ServicePrevu service = new ServicePrevu(ue, volumeCM, volumeTD, volumeTP);
            lesServicesPrevus.add(service);
        } else {
            service.setVolumeCM(service.getVolumeCM() + volumeCM);
            service.setVolumeTD(service.getVolumeTD() + volumeTD);
            service.setVolumeTP(service.getVolumeTP() + volumeTP);
        }
    }


    public void ajouteIntervention(Intervention intervention) throws Exception {
        if (!lesEnseignements.containsKey(intervention.getUe())) {
            throw new IllegalArgumentException("La matière ne fait pas partie de l'enseignement");
        }
        lesInterventions.add(intervention);
    }

    public int resteAPlanifier(UE ue, TypeIntervention typeInter) {
        double heurePlanifie = 0;

        ServicePrevu s = lesEnseignements.get(ue);
        if (s == null) {
            return 0;
        }

        double heureAPlanifie = s.getVolume(typeInter);

        for (Intervention inter : lesInterventions) {
            if ((ue.equals(inter.getUe())) && (typeInter.equals(inter.getTypeInter()))) {
                heurePlanifie += inter.getDuree();
            }
        }
        return (int) Math.round(Math.abs(heurePlanifie - heureAPlanifie));
    }


}
