import java.util.ArrayList;
import java.util.BitSet;
import java.io.*;

import javax.lang.model.util.ElementScanner6;
import javax.swing.CellEditor;
import javax.swing.JOptionPane;

public class Decodificar{

    private static ArrayList<Integer> bloque = new ArrayList<Integer>();
    private static ArrayList<Integer> aux = new ArrayList<Integer>();
    private static ArrayList<Integer> informacion = new ArrayList<Integer>();
    
    private static File archivo;
    private static FileReader fr;
    private static BufferedReader br;

    private static String nombre;

    public static void leerArchivo(int bits,boolean error) {//TIRA UNA EXCEPCION EN CASO DE NO ENCONTRAR UN ARCHIVO
        int bControl = (int)(Math.log(bits)/Math.log(2));

        try{


            if(error){
                if(bits == 32){//Obetener la ruta del archivo correspondiente
                     archivo = new File("C:\\Users\\Carolina\\Desktop\\HamingFinal\\HE1.txt");
                     nombre = "DEcE1.txt";
                 }
                 else if(bits == 2048){
                     archivo = new File("C:\\Users\\Carolina\\Desktop\\HamingFinal\\HE2.txt");
                     nombre = "DEcE2.txt";
                 }
                 else{
                     archivo = new File("C:\\Users\\Carolina\\Desktop\\HamingFinal\\HE3.txt");
                     nombre = "DEcE3.txt";
                 }

            }else{

                if(bits == 32){//Obetener la ruta del archivo correspondiente
                    System.out.println("aca");
                     archivo = new File("C:\\Users\\Carolina\\Desktop\\HamingFinal\\HA1.txt");
                     nombre = "DE1.txt";
                 }
                 else if(bits == 2048){
                     archivo = new File("C:\\Users\\Carolina\\Desktop\\HamingFinal\\HA2.txt");
                     nombre = "DE2.txt";
                 }
                 else{
                     archivo = new File("C:\\Users\\Carolina\\Desktop\\HamingFinal\\HA3.txt");
                     nombre = "DE3.txt";
                 }
     
            }
       

            if(archivo.exists() & archivo.isFile()){//Si existe la ruta y es un archivo

                System.out.println("\nEl archivo codificado existe\n");

                bloque.clear();
                informacion.clear();
                aux.clear();

                FileInputStream fi = new FileInputStream(archivo);

                int byt = fi.read();

                while(byt != -1){//Si el caracter no es nulo

                    //if((char)caract != '?'){
                    aux = funciones.CaracterToBits(byt);
                    
                    for(int i:aux){
                        bloque.add(i);
                    }

                    if( bloque.size() == bits){//Completamos un bloque pero con bits de control

                            //Sacar errores en caso de que venga con error
                        if(error){
                            bloque = funciones.bloqueCorregido(bloque, bControl);
                        }
                        //
                        aux = funciones.decodificar(bloque);//Decodficamos el bloque y agregamos la informacion en un nuevo arreglo
                        bloque.clear();//Reiniciamos el bloque
                        for(int i:aux){
                            informacion.add(i);
                        }
                    
                    }

                    byt = fi.read();
                }

                fi.close();

                if(!bloque.isEmpty()){//Nos quedamos con un bloque sin completar
                    //
                    if(error){
                        bControl = (int)(Math.log(bloque.size())/Math.log(2));
                        bloque = funciones.bloqueCorregido(bloque, bControl);
                    }
                    //
                    while( bloque.size() < bits){//Lo completamos con 0
                        bloque.add(0);
                    }
                    
                    aux = funciones.decodificar(bloque);
                    bloque.clear();
                    for(int i:aux){
                        informacion.add(i);
                    }

                }//Ya completamos el ultimo bloque codificado

                while( (informacion.size()%8) !=0 ){
                    informacion.add(0);
                }

                Archivo.escribir(nombre, informacion);


            }

            else{//Si no existe el archivo
                System.out.println("\nEl archivo codificado correspondiente no existe\n");
            }

        
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}