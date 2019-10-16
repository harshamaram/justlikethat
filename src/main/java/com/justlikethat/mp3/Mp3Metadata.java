package com.justlikethat.mp3;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;

public class Mp3Metadata {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			AudioFile f = AudioFileIO.read(new File("C:\\Users\\Hello\\Music\\English\\Aqua - Aquarium\\Barbie Girl.mp3"));
			Tag tag = f.getTag();
			Iterator<TagField> fieldsItr = tag.getFields();
			
			System.out.println("--");
			while(fieldsItr.hasNext()) {
				System.out.println(fieldsItr.next().toString());
			}
			System.out.println("--");
			tag.setField(FieldKey.ARTIST,"Aqua");
			f.commit();
		} catch(InvalidAudioFrameException iafe) {
			iafe.printStackTrace();
		} catch (CannotReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TagException e) {
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			e.printStackTrace();
		} catch (CannotWriteException e) {
			e.printStackTrace();
		}
		
	}

}
