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
			double magic = (float) Math.pow(10, n-1);
			float result = (float) (Math.round(value * magic) /magic);
			return result;
		}
	}
}
