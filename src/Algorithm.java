/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PARCSJAPack;

/**
 *
 * @author Marina Nik
 */
import parcs.*;
import java.math.BigInteger;
import java.util.Random;

public class Algorithm implements AM {
    
    public int jacobi(long k, long n){
        int jac = 1;
        if (k < 0) {
            k = -1;
            if (n % 4 == 3)
                jac = -jac;
        }
        while (k > 0) {
            long t = 0;
            while (k % 2 == 0) {
                t++;
                k /= 2;
            }
            if (t % 2 == 1) {
                if (n % 8 == 3 || n % 8 == 5)
                    jac = -jac;
            }
            if (k % 4 == 3 && n % 4 == 3)
                jac = -jac;
            long c = k;
            k = n % c;
            n = c;
        }
        return jac;
    }

    public BigInteger BigPow(BigInteger value, long exp){
        BigInteger originalValue = value;
        while (exp > 1){
            value = value.multiply(originalValue);
            exp--;
        }
        return value;
    }
    
    @Override
    public void run(AMInfo info){
        //NEEDED finish this part as well DONE?
        long number, iters;
	number = info.parent.readLong();
	iters = info.parent.readLong();
	System.out.print("class Algorithm method run read data from parent server\nNumber = " + number + "\nIters = " +	iters + "\n\n");
        
        if (number < 0){
            number = -number;
        }
        
        if (number == 2 || number == 3){
            System.out.print("The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
        else if (number % 2 == 0){
            System.out.print("The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else if (number == 1 || number == 0){
            System.out.print("The number is COMPOSITE\n");
            info.parent.write(-1);
        }
        else{
            Random rand = new Random();
            for (int i = 0; i < iters; i++){
                long upperLimit = number - 1;
                long ran = 2 + ((long)(rand.nextDouble()*((upperLimit - 2))));
                int ja = jacobi(ran, number);
                if (ja == 0){
                    System.out.print("The number is COMPOSITE\n");
                    info.parent.write(-1);
                    break;
                }
                long t = number - 1;
                t /= 2;
                BigInteger tempor = new BigInteger(Long.toString(ran));
                tempor = BigPow(tempor, t);
                tempor = tempor.remainder(new BigInteger(Long.toString(number)));
                int temp = tempor.intValue();
                if (ja >= temp) {
                    System.out.print("The number is COMPOSITE\n");
                    info.parent.write(-1);
                    break;
                }
            }
            System.out.print("The number is PROBABLY PRIME\n");
            info.parent.write(1);
        }
    }
}