package com.wisnu.ebs.xml;

import com.wisnu.ebs.model.MainModel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ConfigStaxParser {

    static final String CONFIG = "config";
    static final String MAPEL = "mapel";
    static final String GURU = "guru";
    static final String BERKAS = "berkas";
    static final String KELAS = "kelas";

    MainModel model;

    @SuppressWarnings({"unchecked", "null"})
    public void readConfig(String configFile) {
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart() == (CONFIG)) {

                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(MAPEL)) {
                            event = eventReader.nextEvent();
                            model.setMaPel(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(GURU)) {
                        event = eventReader.nextEvent();
                        model.setNamaGuru(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(BERKAS)) {
                        event = eventReader.nextEvent();
                        model.setJumlahBerkas(Integer.parseInt(event.asCharacters().getData()));
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(KELAS)) {
                        event = eventReader.nextEvent();
                        model.setNamaKelas(event.asCharacters().getData());
                        continue;
                    }

                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart() == (CONFIG)) {

                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    public void setModel(MainModel model) {
        this.model = model;
    }
    
    

}
