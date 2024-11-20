package champollion;

import lombok.Getter;

@Getter
public class UE {
    private final String myIntitule;
    private int heureCM;
    private int heureTD;
    private int heureTP;

    public UE(String intitule) {
        this.myIntitule = intitule;
    }


    public UE(String intitule, int heureCM, int heureTD, int heureTP) {
        this.myIntitule = intitule;
        this.heureCM = heureCM;
        this.heureTD = heureTD;
        this.heureTP = heureTP;
    }
    
}
