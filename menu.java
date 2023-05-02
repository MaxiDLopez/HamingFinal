import java.util.BitSet;
import java.io.*;
import java.io.FileInputStream;
import java.util.*;

public class menu {
    
    public static void main(String[] args) {

        Scanner ing = new Scanner(System.in);
        
        int opc, opc2;

        do{

            System.out.println("PROYECTO HAMING\n");
            do{

                System.out.println("<1> PROTEGER ARCHIVO");
                System.out.println("<2> PROTEGER ARCHIVO CON ERRORES");
                System.out.println("<3> DECODIFI1CAR ARCHIVO SIN CORREGIR");
                System.out.println("<4> DECODIFICAR ARCHIVO CORREGIDO");
                System.out.println("<5> SALIR");

                System.out.println("\nOPCION: ");
                opc = ing.nextInt();

            }while(opc<1 || opc>5);

            switch(opc){
            
                case 1:{//PROTEGER ARCHIVO

                    do{

                        System.out.println("<1> BLOQUE DE 32 BITS");
                        System.out.println("<2> BLOQUE DE 2048 BITS");;
                        System.out.println("<3> BLOQUE DE 65536 BITS");
                        System.out.println("<4> SALIR");
        
                        System.out.println("\nOPCION: ");
                        opc2 = ing.nextInt();
        
                    }while(opc2<1 || opc2>4);

                    switch(opc2){
                    
                        case 1:{//BLOQUE DE 32 BITS

                            Archivo.proteger(32, (int)(Math.log(32)/Math.log(2)), false);
                            System.out.println("\nARCHIVO DE 32 BITS PROTEGIDO\n");
                        
                        }break;

                        case 2:{//BLOQUE DE 2048 BITS

                            Archivo.proteger(2048, (int)(Math.log(2048)/Math.log(2)), false);
                            System.out.println("\nARCHIVO DE 2048 BITS PROTEGIDO\n");
                        
                        }break;

                        case 3:{//BLOQUE DE 65536 BITS

                            Archivo.proteger(65536, (int)(Math.log(65536)/Math.log(2)), false);
                            System.out.println("\nARCHIVO DE 65536 BITS PROTEGIDO\n");
                        
                        }break;
                    
                    }
                
                }break;


                case 2:{//PROTEGER ARCHIVO CON ERRORES

                    do{

                        System.out.println("<1> BLOQUE DE 32 BITS");
                        System.out.println("<2> BLOQUE DE 2048 BITS");;
                        System.out.println("<3> BLOQUE DE 65536 BITS");
                        System.out.println("<4> SALIR");
        
                        System.out.println("\nOPCION: ");
                        opc2 = ing.nextInt();
        
                    }while(opc2<1 || opc2>4);

                    switch(opc2){
                    
                        case 1:{//BLOQUE DE 32 BITS

                            Archivo.proteger(32, (int)(Math.log(32)/Math.log(2)), true);
                            System.out.println("\nARCHIVO DE 32 BITS PROTEGIDO CON ERRORES\n");
                            
                        
                        }break;

                        case 2:{//BLOQUE DE 2048 BITS

                            Archivo.proteger(2048, (int)(Math.log(2048)/Math.log(2)), true);
                            System.out.println("\nARCHIVO DE 2048 BITS PROTEGIDO CON ERRORES\n");
                        
                        }break;

                        case 3:{//BLOQUE DE 65536 BITS

                            Archivo.proteger(65536, (int)(Math.log(65536)/Math.log(2)), true);
                            System.out.println("\nARCHIVO DE 65536 BITS PROTEGIDO CON ERRORES\n");
                        
                        }break;
                    
                    }
                
                }break;

                case 3:{//DECODIFICAR SIN CORREGIR

                    do{

                        System.out.println("<1> BLOQUE DE 32 BITS");
                        System.out.println("<2> BLOQUE DE 2048 BITS");;
                        System.out.println("<3> BLOQUE DE 65536 BITS");
                        System.out.println("<4> SALIR");
        
                        System.out.println("\nOPCION: ");
                        opc2 = ing.nextInt();
        
                    }while(opc2<1 || opc2>4);

                    switch(opc2){
                    
                        case 1:{

                            Archivo.leer(32,(int)(Math.log(32)/Math.log(2)), false);
                            System.out.println("\nARCHIVO DE 32 BITS DECODIFICADO\n");
                            
                        
                        }break;

                        case 2:{

                            Archivo.leer(2048,(int)(Math.log(2048)/Math.log(2)), false);
                            System.out.println("\nARCHIVO DE 2048 BITS DECODIFICADO\n");
                        
                        }break;

                        case 3:{

                            Archivo.leer(65536,(int)(Math.log(65536)/Math.log(2)), false);
                            System.out.println("\nARCHIVO DE 65536 BITS DECODIFICADO\n");
                        
                        }break;

                    }
                    
                
                }break;

                case 4:{//DECOFICAR CORREGIDO

                    do{

                        System.out.println("<1> BLOQUE DE 32 BITS");
                        System.out.println("<2> BLOQUE DE 2048 BITS");;
                        System.out.println("<3> BLOQUE DE 65536 BITS");
                        System.out.println("<4> SALIR");
        
                        System.out.println("\nOPCION: ");
                        opc2 = ing.nextInt();
        
                    }while(opc2<1 || opc2>4);

                    switch(opc2){
                    
                        case 1:{

                            Archivo.leer(32,(int)(Math.log(32)/Math.log(2)), true);
                            System.out.println("\nARCHIVO DE 32 BITS DECODIFICADO CORREGIDO\n");
                        
                        }break;

                        case 2:{

                            Archivo.leer(2048,(int)(Math.log(2048)/Math.log(2)), true);
                            System.out.println("\nARCHIVO DE 2048 BITS DECODIFICADO CORREGIDO\n");
                        
                        }break;

                        case 3:{

                            Archivo.leer(65536,(int)(Math.log(65536)/Math.log(2)), true);
                            System.out.println("\nARCHIVO DE 65536 BITS DECODIFICADO CORREGIDO\n");
                        
                        }break;

                    }
                
                }break;

            }
        
        }while(opc != 5);

              
    }
       
      
}
