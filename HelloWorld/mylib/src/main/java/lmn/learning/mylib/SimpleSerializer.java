package lmn.learning.mylib;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.StringWriter;

public class SimpleSerializer {
    public static String toXml() {
        StringWriter stringWriter = new StringWriter();
        Serializer serializer = new Persister();
        Example example = new Example("lmn.simple.Example message", 123);
        File result = new File("example.xml");

        try {
            serializer.write(example, result);
            serializer.write(example, stringWriter);
            System.out.println(stringWriter.toString());
            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        //
        System.out.println(toXml());
    }
}
@Root
class Example {

    @Element
    private String text;

    @Attribute
    private int index;

    public Example() {
        super();
    }

    public Example(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public String getMessage() {
        return text;
    }

    public int getId() {
        return index;
    }
}