package Utils;

// class to store some longer than normal math logic for reusability purposes
public class MathUtils {
    public static float getRemainder(float number) {
        return number > 0 ? (float)Math.abs(number - Math.floor(number)) : (float)Math.abs(number - Math.ceil(number));
    }
}
