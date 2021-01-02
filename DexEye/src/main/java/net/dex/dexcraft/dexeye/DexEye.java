package net.dex.dexcraft.dexeye;


import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;


/**
* @author Dex
* @since 02/01/2021
* @version v1.0.0-210102-3
*
* Watches if DexCraft Background Services wasn't terminated on purpose.
*/
public class DexEye
{
  private static long WATCH_TIME_MS = 1000;

  public static void main(String[] args)
  {
    try
    {
      File dcbs = new File("C:/DexCraft/launcher/DexCraftBackgroundServices.jar");
      File dcbsOld = new File("C:/DexCraft/launcher/DexCraftBackgroundServices.old");
      File executions = new File("C:/DexCraft/launcher/executions.dc");
      List<String> readList = FileUtils.readLines(executions, "UTF-8");
      int loop = 0;
      while (loop == 0)
      {
        if (dcbs.renameTo(dcbsOld))
        {
          System.out.println("ERRO - DCBS NÃO ESTÁ SENDO EXECUTADO! TODAS AS APLICAÇÕES JAVA SERÃO FECHADAS.");
          dcbsOld.renameTo(dcbs);
          readList.forEach((item)->
          {
            terminate(item + ".exe");
          });
        }
        Thread.sleep(WATCH_TIME_MS);
      }
    }
    catch (IOException | InterruptedException ex)
    {
      System.out.println("ERRO - " + ex.getMessage());
    }
  }


  /**
   * Terminates a program.
   * @param program the executable program name
   */
  public static void terminate(String program)
  {
    File rtm = new File("C:/DexCraft/launcher/rtm");
    try
    {
      new ProcessBuilder("cmd", "/c", "taskkill /im " + program + " /f /t").directory(rtm).start();
      System.out.println("PROCESSO " + program + " FINALIZADO");
    }
    catch (IOException ex)
    {
      System.out.println("ERRO - " + ex.getMessage());
    }
  }

}
