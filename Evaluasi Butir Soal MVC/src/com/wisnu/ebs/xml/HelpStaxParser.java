package com.wisnu.ebs.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class HelpStaxParser {

    static final String ITEM = "item";
    static final String ID = "id";
    static final String JUDUL = "judul";
    static final String ISI = "isi";

    @SuppressWarnings({"unchecked", "null"})
    public List<Help> readConfig() {
        String configFile = "./src/Resources/Help.xml";
        List<Help> helps = new ArrayList<Help>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Help help = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(ITEM)) {
                        help = new Help();
                        help.setContent(new String[2]);
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(ID)) {

                            }

                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(JUDUL)) {
                            event = eventReader.nextEvent();
                            help.getContent()[0] = event.asCharacters().getData();
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(ISI)) {
                        event = eventReader.nextEvent();
                        help.getContent()[1] = event.asCharacters().getData().replace("LINE", "<br>");
                        continue;
                    }

                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ITEM)) {
                        helps.add(help);
                    }
                }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return helps;
    }

}
