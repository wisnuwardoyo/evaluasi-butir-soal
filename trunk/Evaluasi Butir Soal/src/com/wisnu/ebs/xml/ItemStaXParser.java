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

public class ItemStaXParser {

    static final String ITEM = "item";
    static final String ID = "id";
    static final String KOMPETENSI = "kompetensi";
    static final String JMLSISWA = "siswa";
    static final String JMLSOAL = "jmlSoal";
    static final String TIPE = "tipe";
    static final String KKM = "kkm";
    static final String KUNCI = "kunci";
    static final String SOAL = "soal";


    @SuppressWarnings({"unchecked", "null"})
    public List<Item> readConfig(String configFile) {
        List<Item> items = new ArrayList<Item>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(configFile);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            Item item = null;
            int jmlSiswa = 0;
            int jmlSoal = 0;
            int loopI = 0;
            int loopJ = 0;
            int loopK = 0;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an item element, we create a new item
                    if (startElement.getName().getLocalPart().equals(ITEM)) {
                        item = new Item();
                        jmlSiswa = 0;
                        jmlSoal = 0;
                        loopI = 0;
                        loopJ = 0;
                        loopK = 0;
                        Iterator<Attribute> attributes = startElement
                                .getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(ID)) {
                                item.setId(attribute.getValue());
                            }

                        }
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(KOMPETENSI)) {
                            event = eventReader.nextEvent();
                            item.setKompetensi(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(JMLSISWA)) {
                        event = eventReader.nextEvent();
                        item.setJumlahSiswa(event.asCharacters().getData());
                        jmlSiswa = Integer.parseInt(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(JMLSOAL)) {
                        event = eventReader.nextEvent();
                        item.setJumlahSoal(event.asCharacters().getData());
                        jmlSoal = Integer.parseInt(event.asCharacters().getData());
                        item.setKunci(new String[jmlSoal]);
                         item.setSoal(new String[jmlSiswa][jmlSoal+1]);
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(TIPE)) {
                        event = eventReader.nextEvent();
                        item.setTipe(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(KKM)) {
                        event = eventReader.nextEvent();
                        item.setKkm(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(KUNCI)) {
                        event = eventReader.nextEvent();
                        item.getKunci()[loopI] = event.asCharacters().getData().equals("?") ? "" : event.asCharacters().getData();
                        loopI++;
                        continue;
                    }
                    
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(SOAL)) {
                        event = eventReader.nextEvent();
                       
                        
                        if(loopK <= jmlSoal){
                            item.getSoal()[loopJ][loopK] = event.asCharacters().getData().equals("?") ? "" : event.asCharacters().getData();
                            loopK++;
                        }else{
                            loopJ++;
                            loopK = 0;
                            item.getSoal()[loopJ][loopK] = event.asCharacters().getData().equals("?") ? "" : event.asCharacters().getData();
                            loopK++;
                        }
                        
                        continue;
                    }
                }
                // If we reach the end of an item element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(ITEM)) {
                        items.add(item);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return items;
    }

}
