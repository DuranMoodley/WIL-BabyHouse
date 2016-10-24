/*
FactoryClass.java
Called in other classes, to read data from text files
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FactoryClass
{
    private final Context currentClass;
    //**************************************************************************
    public FactoryClass (Context con)
    {
        currentClass = con;
    }
    //****************************************************************************
    public String readDate(String nameOfTextFile)
    {
        //Reads from a text file located in the assets folder
        BufferedReader objRead = null;
        String textFileData = "";
        String line;
        try {
            objRead = new BufferedReader(new InputStreamReader(currentClass.getAssets().open(nameOfTextFile)));

            while((line = objRead.readLine()) != null)
            {
                textFileData += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (objRead != null)
                {
                    objRead.close();
                }
            } catch (IOException E) {
                E.printStackTrace();
            }
        }
        return textFileData;
    }
    //**************************************************************************
}
