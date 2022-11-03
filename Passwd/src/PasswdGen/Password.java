package PasswdGen;

import java.util.Random;

public class Password {
    public String generate(){
        int leftLimit = 33;
        int rightLimit = 126;
        int targetStringLength = 16;
        int specialLimit = 0;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
            if(randomLimitedInt < 65 || randomLimitedInt > 90 && randomLimitedInt < 97 || randomLimitedInt > 122)
                specialLimit++;
        }
        String generatedString = buffer.toString();
        if(specialLimit < 5){
            generate();
        }
        return generatedString;
    }
}
