package primitives;

public class Material {

    private final double _kD;
    private final double _kS;
    private final int _nShininess;
    private final double _kr;
    private final double _kt;

    /**
     * constructorr
     *
     * @param kD
     * @param kS
     * @param nShininess
     * @param kt
     * @param kr
     */
    public Material(double kD, double kS, int nShininess, double kt, double kr) {
        this._kD = kD;
        this._kS = kS;
        this._nShininess = nShininess;
        this._kt = kt;
        this._kr = kr;
    }

    /**
     * constructor
     *
     * @param kD
     * @param kS
     * @param nShininess
     */
    public Material(double kD, double kS, int nShininess) {
        this(kD, kS, nShininess, 0, 0);
    }

    /**
     * constructor
     *
     * @param material
     */
    public Material(Material material) {
        this(material._kD, material._kS, material._nShininess, material._kt, material._kr);
    }

    /**
     * get nShininess
     *
     * @return int
     */
    public int getnShininess() {
        return _nShininess;
    }

    /**
     * get Kd
     *
     * @return double
     */
    public double getkD() {
        return _kD;
    }

    /**
     * get Ks
     *
     * @return double
     */
    public double getkS() {
        return _kS;
    }

    /**
     * Get Kr
     *
     * @return double
     */
    public double getKr() {
        return _kr;
    }

    /**
     * get Kt
     *
     * @return double
     */
    public double getKt() {
        return _kt;
    }
}