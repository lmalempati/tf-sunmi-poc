package lmn.learning;

import static jdk.xml.internal.SecuritySupport.getClassLoader;

import org.ietf.jgss.GSSContext;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



public class CarClient {
    private static final String PATH = "D:\\Java\\POCBuilder\\out\\artifacts\\POCBuilder_jar\\POCBuilder.jar";
//    private static final String PATH = "D:\\Java\\POCBuilder\\out\\artifacts\\POCBuilder_jar\\POCBuilder.jar";
//private static final String PATH = "https://github.com/lmalempati/tf-sunmi-poc/blob/master/POCBuilder.jar";
//    private static final String PATH = "";

    private static final String CLASS = "lmn.learning.";
    public static Car getCar(String name) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File jarFile = new File(PATH);
        URLClassLoader cl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
        Class carClass = cl.loadClass(CLASS + name);
//        Class carClass = Class.forName(CLASS + name, true, cl);
        return (Car) carClass.getDeclaredConstructor().newInstance();
    }

    public static Vehicle getVehicle(String name) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File jarFile = new File(PATH);
        URLClassLoader cl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
        Class carClass = Class.forName(CLASS + name, true, cl);
        return (Vehicle) carClass.getDeclaredConstructor().newInstance();
    }
    public static IGreet getGreet(String name) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File jarFile = new File(PATH);
        URLClassLoader cl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
        Class carClass = cl.loadClass("lmn.learning." + name);
//        Class carClass = Class.forName(CLASS + name, true, cl);
        return (IGreet) carClass.getDeclaredConstructor().newInstance();
    }

    static Object LoadJar(String clsName) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JarFile jarFile = new JarFile(PATH);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + PATH+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }

            // -6 because of .class
            // je.getRealName();
            String className = je.getName().substring(0,je.getName().length()-6);
//            if className = je.getName().substring(0,je.getName().length()-6);
            String[] s = className.split("/");
            className = className.replace('/', '.');
            if (clsName.equals(s[s.length  -1])){
                Class c = cl.loadClass(className);
                return c.getDeclaredConstructor().newInstance();
//
            }
            System.out.println(className);

        }
        return null;
    }

    public static void main(String[] args) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        //getCar("Sedan");
        //LoadJar("Sedan");
        System.out.println(testCar());
    }
//    public static void main(String[] args) {
//
//        try {
//            IGreet greet = getGreet("GreetMe");
//            System.out.println(greet.Greet("Test Name"));
//
//            Vehicle Sedan = getVehicle("Sedan");
//            Sedan.start();
//            Sedan.stop();
//
//            Vehicle SUV = getVehicle("SUV");
//            SUV.start();
//            SUV.stop();
//
//            // get Vehicle objs
//            Car cSUV = getCar("SUV");
//            System.out.println(" SUV cc: " + cSUV.getEngineCC());
//            Engine engine = new Engine(1299);
//            engine.Standard = "BS5";
//            cSUV.setEngine(engine);
//
//            Car cSedan = getCar("Sedan");
//            System.out.println(" Sedan cc: " + cSedan.getEngineCC());
//
//            engine = new Engine(1199);
//            engine.Standard = "BS6";
//            cSedan.setEngine(engine);
//
//            System.out.println(testCar());
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    public static String testCar() {
        Vehicle Sedan = null;
        StringBuilder sb = new StringBuilder();
        try {
            Sedan = getVehicle("Sedan");
            sb.append(Sedan.start() + "\n");
            sb.append(Sedan.stop() + "\n");

            Vehicle SUV = getVehicle("SUV");
            sb.append(SUV.start() + "\n");
            sb.append(SUV.stop() + "\n");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    }

