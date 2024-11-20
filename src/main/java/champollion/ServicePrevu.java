package champollion;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ServicePrevu {
    private UE ue;
    private Enseignant enseignant;
    @Setter
    private int volumeCM;
    @Setter
    private int volumeTD;
    @Setter
    private int volumeTP;

    public ServicePrevu(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        this.ue = ue;
        this.volumeCM = volumeCM;
        this.volumeTD = volumeTD;
        this.volumeTP = volumeTP;
    }

    public int getVolume(TypeIntervention type) {
        return switch (type) {
            case CM -> getVolumeCM();
            case TD -> getVolumeTD();
            case TP -> getVolumeTP();
        };
    }

    @Override
    public String toString() {
        return "ServicePrevu : \n" + "volumeCM = " + this.volumeCM + ", volumeTD = " + this.volumeTD + ", volumeTP = " + this.volumeTP;
    }



}
