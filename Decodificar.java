import java.util.ArrayList;
import java.util.BitSet;
import java.io.*;

import javax.lang.model.util.ElementScanner6;
import javax.swing.JOptionPane;

public class Decodificar{

    private static int caract;

    private static ArrayList<Integer> bloque = new ArrayList<Integer>();
    private static ArrayList<Integer> aux = new ArrayList<Integer>();
    private static ArrayList<Integer> informacion = new ArrayList<Integer>();;
    private static ArrayList<Integer> contenedor = new ArrayList<Integer>();
    
    private static File archivo;
    private static FileReader fr;
    private static BufferedReader br;

    private static String nombre;

    public static void leerArchivo(int bits) {//TIRA UNA EXCEPCION EN CASO DE NO ENCONTRAR UN ARCHIVO


        try{

            if(bits == 32){//Obetener la ruta del archivo correspondiente
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\HA1.txt");
                nombre = "DE1.txt";
            }
            else if(bits == 2048){
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\HA2.txt");
                nombre = "DE2.txt";
            }
            else{
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\HA3.txt");
                nombre = "DE3.txt";
            }

            if(archivo.exists() & archivo.isFile()){//Si existe la ruta y es un archivo

                System.out.println("\nEl archivo codificado existe\n");

                bloque.clear();
                informacion.clear();
                contenedor.clear();
                aux.clear();

                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                caract=br.read();

                while(caract != -1){//Si el caracter no es nulo
                
                    aux = funciones.CaracterToBits(caract);

                    for(int i:aux){
                        bloque.add(i);
                    }

                    System.out.println("Bloque: "+bloque);

                    if( bloque.size() == bits){//Completamos un bloque pero con bits de control

                        aux = funciones.decodificar(bloque);//Decodficamos el bloque y agregamos la informacion en un nuevo arreglo
                        bloque.clear();//Reiniciamos el bloque
                        for(int i:aux){
                            informacion.add(i);
                        }

                        System.out.println("Informacion: "+informacion);
                        System.out.println("Informacion cantidad: "+ informacion.size());

                        if( informacion.size() >= bits){//Completamos un bloque solamente de informacion

                            while( contenedor.size() < bits ){

                                contenedor.add(informacion.get(0));//Metemos el primer bit del arreglo aux
                                informacion.remove(0);//Borramos ese elemento que ingresamos
                            
                            }

                            Archivo.escribir(nombre, contenedor);
                            System.out.println("contenedor: "+contenedor);
                            System.out.println("Informacion: "+informacion);
                            System.out.println("Informacion cantidad: "+ informacion.size());
                            contenedor.clear();
                        }
                    
                    }

                    caract=br.read();
                }

                if(!bloque.isEmpty()){//Nos quedamos con un bloque sin completar

                    System.out.println("\nQUEDARON BITS EN EL BLoQOUE\n");
                    
                    while( bloque.size() < bits){//Lo completamos con 0
                        bloque.add(0);
                    }
                    
                    informacion = funciones.decodificar(bloque);
                    bloque.clear();

                }//Ya completamos el ultimo bloque codificado

                if ( informacion.size() > bits ){//Hay mas de un caracter para imprimir

                    System.out.println("\nQUEDARON MAS DE UN CARACTER EN Informacion\n");

                    while( contenedor.size() < bits ){

                        contenedor.add(informacion.get(0));//Metemos el primer bit del arreglo aux
                        informacion.remove(0);//Borramos ese elemento que ingresamos
                    
                    }
                    Archivo.escribir(nombre, contenedor);
                    System.out.println(contenedor);
                    contenedor.clear();
                
                }

                if(!informacion.isEmpty() & informacion.contains(1)){
                    
                    System.out.println("\nQUEDARON " + informacion.size() + " bits en informacion \n");
                    while( informacion.size() < bits){
                        informacion.add(0);
                    }

                    Archivo.escribir(nombre, informacion);
                    System.out.println(informacion);
                    informacion.clear();
                }

 
            }

            else{//Si no existe el archivo
                System.out.println("\nEl archivo codificado correspondiente no existe\n");
            }

        
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}