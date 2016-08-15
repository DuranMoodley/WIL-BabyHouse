package lalucia.babyhouse.babyhouse;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FactoryClass
{
    private String textFileName;
    private final Context currentClass;
    //**************************************************************************
    public FactoryClass(Context con , String nameOfTextFile)
    {
        currentClass = con;
        textFileName = nameOfTextFile;
    }
    //**************************************************************************
    public FactoryClass (Context con)
    {
        currentClass = con;
    }
    //**************************************************************************
    public void writeDate(String userVal)
    {
        //Writes the string data defined in the previous method to a text file
        BufferedWriter objWriter = null;
        try {
            FileOutputStream outputStream = currentClass.openFileOutput(textFileName, Context.MODE_APPEND);
            objWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            objWriter.write(userVal);
            objWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                assert objWriter != null;
                objWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //**************************************************************************
    public ArrayList<String> readData()
    {
        BufferedReader objRead = null;
        ArrayList<String> objData = new ArrayList<>();
        try {
            //Get Stream from file
            FileInputStream objInput = currentClass.openFileInput(textFileName);
            objRead = new BufferedReader(new InputStreamReader(objInput));
            String line;

            //Read each line and insert it to the array list
            while ((line = objRead.readLine()) != null)
            {
                objData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objRead != null)
                {
                    objRead.close();
                }
            } catch (IOException E) {
                E.printStackTrace();
            }
        }
        return objData;
    }
    //**************************************************************************
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
    public ArrayList readValidWordsData()
    {
        BufferedReader objRead = null;
        ArrayList<String> objData = new ArrayList<>();
        String line;
        try {
            objRead = new BufferedReader(new InputStreamReader(currentClass.getAssets().open(textFileName)));

            while((line = objRead.readLine()) != null)
            {
                objData.add(line);
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

        return objData;
    }
}
