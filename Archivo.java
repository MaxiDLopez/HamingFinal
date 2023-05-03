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
            System.out.println("sali del while");
              if (sizeAuxiliar != 0 || sizePrincipal != 0) { //Si quedaron cosas sueltas que no entran en un bloque de 32
              ultimo=true;
                if (sizeAuxiliar != 0){
                  //  principal = funciones.agruparBits(principal, auxiliar,0, sizeAuxiliar);
                    principal = funciones.agruparBits(principal, auxiliar, principal.size(), auxiliar.size());
                } 

                principal = funciones.rellenarConCeros(principal, bloque);
                System.out.println("Estoy por aplicar hamming");
                System.out.println("Principal es de tamaño: "+principal.size());
                
               //r valia 5
                sobrante = funciones.aplicarHamming(principal, bitsControl, error);
                
                //Imprimimos la ultima parte
                    
                principal.clear();
                sizePrincipal = 0;
        }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Ha sucedido un error en proteger: "+e);
        }
    }

    public static void leer(int tamañoHamming,int bitsDeControl, boolean corregir){

        FileReader fr;
        BufferedReader br;
        int caract;
      
        int bitsInformacion = tamañoHamming-bitsDeControl-1;    //32-5-1 = 26
        int bitsControl = bitsDeControl;    //  5
        
        int agrupar = (tamañoHamming/8);    // 4

        boolean fin = false;   //Cuando llegue a fin de linea sera true

        int contadorSalidas, posInfo, contAux=0, contEscritor=0;
       
        ArrayList<Integer> info;//26 porque es Hamming 32
        info = new ArrayList<Integer>();

        ArrayList<Integer> auxiliar;
        auxiliar = new ArrayList<Integer>();

        ArrayList<Integer> lectura;
        lectura = new ArrayList<Integer>();

        ArrayList<Integer> escritor;//Me va juntando de a 8 bits
        escritor = new ArrayList<Integer>();
        int a=0;
        int first;
//
        try{
            fr = new FileReader("C:\\Users\\Carolina\\Desktop\\Haming-alternativo\\HA1.txt");
            
            if(fr.ready()){//Si el archivo esta listo para ser leido

                br = new BufferedReader(fr);
                caract = br.read(); //Leemos primer caracter(si es que existe)
                if (caract == -1) {
                    fin = true;
                }

                while(!fin){ //Si este caracter no es nulo
                    
                    for (int j = 0; j < agrupar; j++) { 

                        if(caract == -1){
                            fin = true;
                            info = funciones.rellenarConCeros(info, tamañoHamming);
                            j=agrupar;  //Lo hago para que salga del for
                        }else{
                            lectura = funciones.CaracterToBits(caract);
                            info = funciones.agruparBits(info, lectura, info.size(), 8);
                        }

                        
                        if(!fin){       //Para que no lea una vez que ya leyo fin de linea
                            caract = br.read();
                        }
                    }
                    
                    if (corregir){
                        info = funciones.bloqueCorregido(info, bitsControl);
                    }
                    
                    info = funciones.decodificar(info);
                    posInfo = 0;
                    contadorSalidas = bitsInformacion;  //Es 26 para Hamming 32

                    if(contAux!=0){             //Si contAux no es cero      
                        for(int i=0; i<contAux; i++){       //A escritor le meto los bits que tenga auxiliar
                            escritor.add(auxiliar.get(i));
                        }
            
                        contEscritor=contAux;   //Actualizo tamaños
                        auxiliar.clear();
                        contAux=0;              //Aux se vacio y tiene tamaño cero
            
                        for(int i=0;i<8-contEscritor;i++){              //Si escritor no viene lleno le meto lo que hay en info
                            escritor.add(contEscritor+i,info.get(i) );
                            a=i;
                        }                                           //Ahora escritor tiene 8 bits y esta listo para guardar en txt
            
                        posInfo+= a+1;         //Posicion informacion = Posicion informacion + cantidad que leyo de info
                        contadorSalidas-= a+1;  // ContadorOutput = ContadorOutput - cantidad de bits sacados de info
                        contEscritor=0;     //Tiene 0 porque ya se guardo Escritor
                        escribirCaracter("Decodificado.txt", escritor);
                        escritor.clear();
                    }
            
                    while(contadorSalidas>=8){      //Mientras contadorSalidas sea mayor o igual a 8
                        for(int i=0; i<8; i++){
                            escritor.add(i,info.get(posInfo+i));  //Armar escritor con 8 bits de info
                        }
                        posInfo +=8;
                        contadorSalidas -=8;
                        escribirCaracter("Decodificado.txt", escritor);
                        escritor.clear();
                    }
                  
                    if(contadorSalidas!=0){          //Si no se mandaron todos los bits de info
                        for(int i=0; i<contadorSalidas; i++){
                            auxiliar.add(i,info.get(posInfo+i));    //Meter estos bits sobrantes en auxiliar
                        }
                        info.clear();
                        contAux=contadorSalidas;
                    }
                                
                }


                boolean basura = true;
                int c = 0;
                do {
                    if (auxiliar.get(c) == 1) {
                        basura = false;
                    }
                    c++;
                } while (basura == true && c<8 );

                if (!basura) { //Me quedo algo en auxiliar
                    auxiliar = funciones.rellenarConCeros(auxiliar, 8);
                    escribirCaracter("Decodificado.txt", auxiliar);
                }

            
            }else{
                System.out.println("\nEL ARCHIVO NO ESTA LISTO PARA SER LEIDO \n");
            }


            

        }catch(Exception e){
            System.out.println("\nError: " + e.getMessage());
        }

    
    }
    //
    public static void escribirCaracter(String nuevo, ArrayList<Integer> arreglo){
        
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            
            File f = new File(nuevo);
            
            // Si el archivo no existe, se crea!S
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("\nARCHIVO CREADO\n");
            }


            // flag true, indica adjuntar información al archivo.
            fw = new FileWriter(f.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
                
            fw.write(funciones.convertirInvers(arreglo)[0]);
                

            System.out.println("información agregada!");
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
    //
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