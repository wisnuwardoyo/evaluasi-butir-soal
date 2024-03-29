package com.wisnu.ebs.xml;

import com.wisnu.ebs.model.Database;
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

    Database database;

    @SuppressWarnings({"unchecked", "null"})
    public void readConfig(String configFile) throws FileNotFoundException, XMLStreamException {
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
                        database.setSubject(event.asCharacters().getData());
                        continue;
                    }
                }
                if (event.asStartElement().getName().getLocalPart()
                        .equals(GURU)) {
                    event = eventReader.nextEvent();
                    database.setTeacherName(event.asCharacters().getData());
                    continue;
                }
                if (event.asStartElement().getName().getLocalPart()
                        .equals(BERKAS)) {
                    event = eventReader.nextEvent();
                    database.setFileCount(Integer.parseInt(event.asCharacters().getData()));
                    continue;
                }

                if (event.asStartElement().getName().getLocalPart()
                        .equals(KELAS)) {
                    event = eventReader.nextEvent();
                    database.setClassName(event.asCharacters().getData());
                    continue;
                }

            }

            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart() == (CONFIG)) {

                }
            }

        }

    }

    public void setModel(Database model) {
        this.database = model;
    }

}
