package Utils;

public class MathUtils {
    
	public static float getRemainder(float number) {
        return number > 0 ? (float)Math.abs(number - Math.floor(number)) : (float)Math.abs(number - Math.ceil(number));
    }
	
	public static float roundToNDecimalPlaces(Float value, int n) {
		if (n < 0) {
			System.err.println("Warning, you must use a positive number of decimal places. Aborting rounding operation.");
			return value;
		}
		else {
			double magic = Math.pow(10, n);
			return (float) (Math.round(value * magic) / magic);
		}
	}
}
