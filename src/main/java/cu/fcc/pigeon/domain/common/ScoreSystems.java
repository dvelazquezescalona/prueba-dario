package cu.fcc.pigeon.domain.common;

public class ScoreSystems {

    private int system;
    private int cantPremio;
    private double scoreMax;
    private double scoreMin;
    private double velocMax;
    private double velocMin;

    public ScoreSystems() {}

    public ScoreSystems(int system, int cantPremio, double scoreMax, double scoreMin, double velocMax, double velocMin) {
        this.system = system;
        this.cantPremio = cantPremio;
        this.scoreMax = scoreMax;
        this.scoreMin = scoreMin;
        this.velocMax = velocMax;
        this.velocMin = velocMin;
    }

    public int getSystem() {
        return system;
    }

    public void setSystem(int system) {
        this.system = system;
    }

    public int getCantPremio() {
        return cantPremio;
    }

    public void setCantPremio(int cantPremio) {
        this.cantPremio = cantPremio;
    }

    public double getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(double scoreMax) {
        this.scoreMax = scoreMax;
    }

    public double getScoreMin() {
        return scoreMin;
    }

    public void setScoreMin(double scoreMin) {
        this.scoreMin = scoreMin;
    }

    public double getVelocMax() {
        return velocMax;
    }

    public void setVelocMax(double velocMax) {
        this.velocMax = velocMax;
    }

    public double getVelocMin() {
        return velocMin;
    }

    public void setVelocMin(double velocMin) {
        this.velocMin = velocMin;
    }

    public double score(int lugar, double velocPal) {
        double scorePal = 0;
        switch (system) {
            case 1:
                {
                    scorePal = (scoreMax / velocMax) * velocPal;
                }
                break;
            case 2:
                {
                    scorePal = scoreMax - ((scoreMax - scoreMin) * (velocMax - velocPal)) / (velocMax - velocMin);
                }
                break;
            case 3:
                {
                    scorePal = scoreMax - (((lugar - 1) * (scoreMax - scoreMin)) / (cantPremio - 1));
                }
                break;
        }
        return scorePal;
    }
}
