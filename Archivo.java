import java.util.ArrayList;
import java.util.BitSet;
import java.io.*;

import javax.lang.model.util.ElementScanner6;
import javax.swing.JOptionPane;

public class Archivo{

    public static ArrayList<Integer> arreglo = new ArrayList<Integer>();
    public static ArrayList<Integer> aux;
    public static ArrayList<Character> caracter = new ArrayList<Character>();

    public static void proteger(int bloque, int bitsControl, Boolean error){
        int caract;
        arreglo.clear();

        ArrayList<Integer> auxiliar;
        auxiliar = new ArrayList<Integer>();
        int sizeAuxiliar = 0;

        ArrayList<Integer> principal;//Arreglo bitset en donde vamos a cargar los bits leidos
        principal = new ArrayList<Integer>();
        int sizePrincipal = 0;      //Tamaño inicial

        Boolean ultimo = false;
        ArrayList<Integer> lectura;
        lectura = new ArrayList<Integer>();

        ArrayList<Integer> sobrante;
        sobrante = new ArrayList<Integer>();

        try {
            //FileReader fr = new FileReader("C:\\Users\\Carolina\\Desktop\\MiercolesNoche\\lectura.txt");//Abre el arhicvo
            FileReader fr = new FileReader("C:\\Users\\Carolina\\Desktop\\Haming-alternativo\\texto.txt");//Abre el arhicvo
            BufferedReader br = new BufferedReader(fr);
            caract = br.read();//leer un caracterer

            while(caract != -1) {//Si este caracter es distinto de nulo


                int j=0;

                if (sizeAuxiliar != 0) {        //Si hay bits de la vuelta anterior, entramos al if
                    principal = funciones.agruparBits(principal, auxiliar, 0, auxiliar.size());// esta funcion mete en el bloque el resto del anterior
                    sizePrincipal += sizeAuxiliar;
                    
                    auxiliar.clear();   //Limpio el arreglo donde guardo bits de resto
                    sizeAuxiliar = 0;
                }
                
                if (sizePrincipal + 8 <= bloque) {    //Comprueba que quepan 8 bits en el arreglo
                    
                    lectura = funciones.CaracterToBits(caract); //Convierte el caracter leido a 8 bits
                    principal = funciones.agruparBits(principal, lectura, sizePrincipal, 8);
                    sizePrincipal += 8;
                }
                else {  //Meto solo los que entren
                    auxiliar = funciones.CaracterToBits(caract);
                    sizeAuxiliar = 8;

                    principal = funciones.agruparBits(principal, auxiliar, sizePrincipal,bloque - sizePrincipal);
                    
                    //descarto de auxiliar los bits que ya copié
                    auxiliar = funciones.correrBitsIzq(auxiliar, 8,bloque-sizePrincipal);
                    //actualizo sizes
                    sizeAuxiliar -= (bloque - sizePrincipal);
                    sizePrincipal = bloque;
                }    
                                
                if (sizePrincipal == bloque) { //si array principal está lleno, puede enviarse a la funcion que aplique hamming
                    
                    sobrante = funciones.aplicarHamming(principal, (int)(Math.log(bloque)/Math.log(2)), error);//Sobrante tiene los bits que sobraron,se los debo agregar al vector resto "auxiliar"
                    auxiliar = funciones.agruparBits(auxiliar, sobrante, sizeAuxiliar,sobrante.size());
                    sizeAuxiliar = auxiliar.size();
                    
                    //LO PASAMOS A UN TXT CON RESPECTIVO NOMBRE DEPENDIENDO DEL BLOQUE
                    
                    principal.clear();
                    sizePrincipal = 0;
                }

                caract = br.read();
            }
              
            if (sizeAuxiliar != 0 || sizePrincipal != 0) { //Si quedaron cosas sueltas que no entran en un bloque de 32
                ultimo=true;
                if (sizeAuxiliar != 0){
                    //  principal = funciones.agruparBits(principal, auxiliar,0, sizeAuxiliar);
                    principal = funciones.agruparBits(principal, auxiliar, principal.size(), auxiliar.size());
                } 

                principal = funciones.rellenarConCeros(principal, bloque);
//              System.out.println("Principal es de tamaño: "+principal.size());
                
                sobrante = funciones.aplicarHamming(principal, bitsControl, error);
                
                    
                principal.clear();
                sizePrincipal = 0;
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Ha sucedido un error en proteger: "+e);
        }
    }


    public static void escribir(String nuevo, ArrayList<Integer> bits){
        
        BufferedWriter bw = null;
        FileWriter fw = null;
        caracter.clear();

        try {
            
            File f = new File(nuevo);
            
            // Si el archivo no existe, se crea
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("\nARCHIVO CREADO: " + nuevo + "\n");
            }


            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(f.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            caracter = funciones.bitstoCharacters(bits);
            System.out.println(bits);

            for(char i:caracter){//imprimimos cada caracter leido
                bw.write(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                            //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            
            }catch(Exception e){ //Si muestro un error en la escritura, lo muestra
                    JOptionPane.showMessageDialog(null,"Ha sucedido un error en escribir: "+e);
            }
        }
}

}