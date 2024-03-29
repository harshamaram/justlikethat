package com.justlikethat.mp3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class LoadData {
	
	public static Map<String, String> data = new HashMap<String, String>();
	
	public void readData() {

		try {
			FileInputStream excelFile = new FileInputStream(new File("input_data.xls"));
			Workbook workbook = new HSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			String str1, str2;
			
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Cell cell1 = currentRow.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				Cell cell2 = currentRow.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				if(cell1 == null || cell2 == null) {
					continue;
				}
				
				str1 = cell1.getStringCellValue();
				str2 = cell1.getStringCellValue();

				data.put(str1, str2);

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	static {
			data.put("1942 - A Love Story","R D Burman");
			data.put("3 Idiots","Shantanu Moitra");
			data.put("A.R. Rahman - Pray For Me Brother (2007)","A R Rahman");
			data.put("Aap Ka Abhijeet Saawant","Abhijeet Saawant");
			data.put("Aashiqui","Nadeem-Shravan");
			data.put("Ajab-Prem-Ki-Ghazab-Kahani","Pritam Chakraborty");
			data.put("Asoka","Anu Malik");
			data.put("Baazigar","Anu Malik");
			data.put("Bachna Ae Haseeno","Vishal-Shekhar");
			data.put("Bhool Bhoolaiyaa","Pritam Chakraborty");
			data.put("Bluffmaster","Vishal-Shekhar");
			data.put("Bombay","A R Rahman");
			data.put("BOMBAY 2 GOA","R D Burman");
			data.put("Boom","Talvin Singh");
			data.put("Border","Anu Malik");
			data.put("Bunty aur Babli","Shankar Ehsaan Loy");
			data.put("Chak De India","Salim-Sulaiman");
			data.put("Chalte Chalte","Shankar Ehsaan Loy");
			data.put("Chalti Ka Naam Gadi","Kishore Kumar");
			data.put("Chameli","Sudhir Mishra");
			data.put("Cocktail (2012)","Pritam Chakraborty");
			data.put("Daud","A R Rahman");
			data.put("DDLJ","Jatin Lalit");
			data.put("Delhi 6","A R Rahman");
			data.put("Devdas","Ismail Darbar");
			data.put("DHADKAN","Nadeem-Shravan");
			data.put("DHOOM","Pritam Chakraborty");
			data.put("Dhoom2","Pritam Chakraborty");
			data.put("Dhoom2","Pritam Chakraborty");
			data.put("Dil Chahata Hai","Shankar Ehsaan Loy");
			data.put("Dil Chahta Hai","Shankar Ehsaan Loy");
			data.put("Dil Se","A R Rahman");
			data.put("Dil To Pagal Hai","Uttam Singh");
			data.put("DIL WALE DULHANIA LE JAYENGE","Jatin Lalit");
			data.put("Dillagi","Jatin-Lalit");
			data.put("DILTOPAGAL HAI","Uttam Singh");
			data.put("Dilwale Dulhania Le Jayenge","Jatin Lalit");
			data.put("DON","Shankar Ehsaan Loy");
			data.put("Dostana","Vishal-Shekhar");
			data.put("Fanaa","Jatin Lalit");
			data.put("Fashion","Salim-Sulaiman");
			data.put("Fiza","Anu Malik");
			data.put("GANGESTER","Pritam Chakraborty");
			data.put("Gangster","Pritam Chakraborty");
			data.put("Garam Masala","Pritam Chakraborty");
			data.put("Ghajini","A R Rahman");
			data.put("GHULAM","Jatin Lalit");
			data.put("Gulaal","Piyush Mishra");
			data.put("GURU","A R Rahman");
			data.put("Housefull[2010]","Shankar Ehsaan Loy");
			data.put("Hum Aapke hai Kaoun","Raamlaxman");
			data.put("Hum Dil De Chuke Sanam","Ismail Darbar");
			data.put("Jaane Tu... Ya Najane Na","A R Rahman");
			data.put("Jab we met","Pritam Chakraborty");
			data.put("Jhankar Beats","Vishal-Shekhar");
			data.put("Jo Jeeta Wohi Sikandar","Jatin Lalit");
			data.put("Joddha Akbar","A R Rahman");
			data.put("Jodhaa Akbar","A R Rahman");
			data.put("Kaho Na Pyar Hai","Rajesh Roshan");
			data.put("Kaho Naa Pyar Hai","Rajesh Roshan");
			data.put("Kal ho na ho","Shankar-Ehsaan-Loy");
			data.put("Kal Ho Naa Ho","Shankar-Ehsaan-Loy");
			data.put("Karz","Laxmikant Pyarelal");
			data.put("Khalnayak","Laxmikant Pyarelal");
			data.put("Koi Mil Gaya","Rajesh Roshan");
			data.put("Krrish","Rajesh Roshan");
			data.put("Krrish (2006)","Rajesh Roshan");
			data.put("Kuchh Kuchh Hota Hai","Jatin Lalit");
			data.put("Kyon ki","Himesh Reshammiya");
			data.put("Lagaan","A R Rahman");
			data.put("Lakshya","Shankar Ehsaan Loy");
			data.put("Love Aaj Kal[2009]","Pritam Chakraborty");
			data.put("Lucky Ali","Lucky Ali");
			data.put("Lukey Ali","Lucky Ali");
			data.put("Maachis","Vishal Bhardwaj");
			data.put("Maine Pyar Kiya (1989)","Raamlaxman");
			data.put("Mast","Sandeep Chowta");
			data.put("Mohabbatein","Jatin Lalit");
			data.put("Morning Raaga","Mani Sharma");
			data.put("MTv Collection","MTV");
			data.put("New York","Pritam Chakraborty");
			data.put("Om shanti om","Vishal-Shekhar");
			data.put("Omkara","Pritam Chakraborty");
			data.put("Once Upon A Time In Mumbai - 2010","Pritam Chakraborty");
			data.put("Pardes","Vishal Bhardwaj");
			data.put("Partner","Sajid-Wajid");
			data.put("Pyaar Tune Kya Kiya","Sandeep Chowta");
			data.put("Raavan","A R Rahman");
			data.put("Raavan[2010]","A R Rahman");
			data.put("RAAZ","Nadeem-Shravan");
			data.put("Rang de basanti","A R Rahman");
			data.put("Rangeela","A R Rahman");
			data.put("Rehnaa Hai Terre Dil Mein","Harris Jayaraj");
			data.put("RHTDM","Harris Jayaraj");
			data.put("RockOn","Shankar Ehsaan Loy");
			data.put("Roja","A R Rahman");
			data.put("Rules","Sandesh Shandilya");
			data.put("SAAJAN","Nadeem-Shravan");
			data.put("Saathiya","A R Rahman");
			data.put("Sarfarosh","Jatin Lalit");
			data.put("SD Burman (The Golden Collection)","S D Burman");
			data.put("Sholay","R D Burman");
			data.put("Sikandar (2009)","Shankar Ehsaan Loy");
			data.put("Slumdog Millionaire","A R Rahman");
			data.put("Slumdog Millioneer","A R Rahman");
			data.put("Soldier","Anu Malik");
			data.put("sonu nigam","Sonu Nigam");
			data.put("Swades","A R Rahman");
			data.put("Taal","A R Rahman");
			data.put("Taare Zameen Par","Shankar Ehsaan Loy");
			data.put("Taxi No 9211","Vishal-Shekhar");
			data.put("Taxi No. 9211","Vishal-Shekhar");
			data.put("THE LEGEND OF BHAGAT SINGH","A R Rahman");
			data.put("TUM BIN","Nikhil-Vinay");
			data.put("Umrao Jaan","Khayyam");
			data.put("Vande Mataram - A.R.RAHMAN","A R Rahman");
			data.put("Vande Matram","A R Rahman");
			data.put("Vandemataram","A R Rahman");
			data.put("Wake-Up-Sid[2009]","Shankar Ehsaan Loy");
			data.put("Yes Boss","Jatin Lalit");
			data.put("YUVA","A R Rahman");
			data.put("Zamane ko dikhana hai","R D Burman");
			data.put("Ilayaraja_Instrumental","Ilayaraja");
			data.put("Instrumental","Instrumental");
			data.put("Kishore","Kishore Kumar");
			data.put("Kishore Lata","Kishore Lata");
			data.put("Kishore Lata","Kishore Lata");
			data.put("KISHORE","Kishore Kumar");
			data.put("LATA","LATA");
			data.put("MUKESH","MUKESH");
			data.put("RAFI","RAFI");
			data.put("Rafi Asha","Rafi Asha");
			data.put("Rafi-Lata","Rafi Asha");
			data.put("Rafi","Rafi");
			data.put("Rafi","Rafi");
			data.put("Raj Kapoor","Raj Kapoor");
			data.put("Romanian","Romanian");
			data.put("selected-hindi","selected-hindi");
			data.put("7 G Brundhavana Colony","Yuan Shankar Raja");
			data.put("Aaduvaarimaatalaku Ardaale Verule","Yuan Shankar Raja");
			data.put("Aakali Rajyam","M S Viswanathan");
			data.put("Aaradhana","Ilayaraja");
			data.put("Aaradhana Chiru","Ilayaraja");
			data.put("Aaru","Devi Sri Prasad");
			data.put("Aarya","Devi Sri Prasad");
			data.put("Aarya-2","Devi Sri Prasad");
			data.put("Aata","Devi Sri Prasad");
			data.put("Abhilasha","Ilayaraja");
			data.put("Abhinandana","Ilayaraja");
			data.put("Aditya 369","Ilayaraja");
			data.put("Aduvaari Maatalaku Arthaaley Veruley","Yuan Shankar Raja");
			data.put("Aha Naa Pellanta","Raghu Kunche");
			data.put("Ala Modalaindi","Kalyani Malik");
			data.put("Ammayilu Abbayilu","Chakri ");
			data.put("Anaganaga Oka Roju","M M Keeravani");
			data.put("Anand","K M Radha Krishnan");
			data.put("Anandam","Devi Sri Prasad");
			data.put("ANNAMAYYA","M M Keeravani");
			data.put("Anukokunda Oka Roju","M M Keeravani");
			data.put("Anveshana","Ilayaraja");
			data.put("Anveshana Old","Ilayaraja");
			data.put("Apadbhandavudu","M M Keeravani");
			data.put("Apathbhandavudu","M M Keeravani");
			data.put("Apuroopam","Chakri ");
			data.put("Arundhati","Koti");
			data.put("Athadu","Mani Sharma");
			data.put("Balu","Mani Sharma");
			data.put("Bhairavadweepam","Madhavapeddi Suresh");
			data.put("Bhalevaadivi Baasu","Mani Sharma");
			data.put("Bharateeyudu","A R Rahman");
			data.put("Bharatheeyudu","A R Rahman");
			data.put("Billa","Yuan Shankar Raja");
			data.put("Bombaipriyudu","M M Keeravani");
			data.put("Bombay","A R Rahman");
			data.put("Bommarillu","Devi Sri Prasad");
			data.put("Boys","A R Rahman");
			data.put("Brindaavanam (2010)","M M Keeravani");
			data.put("Bujjigaadu","Sandeep Chowta");
			data.put("Chandamaama","K M Radha Krishnan");
			data.put("Chandamama","K M Radha Krishnan");
			data.put("Chandramukhi","Vidyasagar");
			data.put("Chanti, Dalapathi","Ilayaraja");
			data.put("Chatrapathi","Ilayaraja");
			data.put("Chatrapati","M M Keeravani");
			data.put("Cheli","Harris Jayaraj");
			data.put("Chettu Kinda Pleader","Ilayaraja");
			data.put("Chiranjeevi Hits","Chiranjeevi Hits");
			data.put("Chirunavvutho,Swayamvaram","Mani Sharma");
			data.put("Chiruta","Mani Sharma");
			data.put("Chocklet","Deva");
			data.put("Chudalani Vundi","Mani Sharma");
			data.put("chukkallo chandrudu","Chakri ");
			data.put("Criminal","M M Keeravani");
			data.put("Dalapathi","Ilayaraja");
			data.put("01vinayaka chaviti","Devotional");
			data.put("02varalaxmi vratam","Devotional");
			data.put("03mangala gowri vratam","Devotional");
			data.put("04satyanarayana vratam","Devotional");
			data.put("05trinada mela","Devotional");
			data.put("06kedara vratam","Devotional");
			data.put("Ayyappa Songs","Devotional");
			data.put("Akhilandeshwara.Ayyappa.2002.MP3.320.kbps.VBR-XM3","Devotional");
			data.put("Devullu","Vandemataram Srinivas");
			data.put("Donga Donga","A R Rahman");
			data.put("Ela Cheppanu","Koti");
			data.put("Garana Mogudu","M M Keeravani");
			data.put("Garshana","Harris Jayaraj");
			data.put("Gayam","Sri Kommineni");
			data.put("Geetanjali","Ilayaraja");
			data.put("Geethanjali","Ilayaraja");
			data.put("Gentle man","A R Rahman");
			data.put("Ghajini","Harris Jayaraj");
			data.put("Gharshana","Harris Jayaraj");
			data.put("Ghatikudu (2009) 128kbps","Harris Jayaraj");
			data.put("Godavari","K M Radha Krishnan");
			data.put("Golconda High School","Kalyani Malik");
			data.put("Goolmaal","Vandemataram");
			data.put("Gopi","Koti");
			data.put("Gopi Gopika Godavari (2009)","Chakri ");
			data.put("Govinda Govinda","Koti");
			data.put("Gudumba Sankar","Mani Sharma");
			data.put("Gudumba Shankar","Mani Sharma");
			data.put("GULABI","Shashi Pritham");
			data.put("Happy","Yuan Shankar Raja");
			data.put("Happy Days","Mickey J Meyer");
			data.put("Hello Brother","Koti");
			data.put("Idiot","Chakri ");
			data.put("Ilayaraja Hits","Ilayaraja");
			data.put("Illaya_Hits","Ilayaraja");
			data.put("Indu","Vidyasagar");
			data.put("Ishq (2012) ~320KBPS","Anoop Rubens");
			data.put("Jagadam","Devi Sri Prasad");
			data.put("Jagadeka Veerudu Athilokasundari","Ilayaraja");
			data.put("Jalsa","Devi Sri Prasad");
			data.put("Jayahey","Chinni Charan");
			data.put("Jayee Bhava(2009)","S Thaman");
			data.put("Jeans","A R Rahman");
			data.put("Kalisundam!Ra!","S A Raj Kumar");
			data.put("Kantri","Mani Sharma");
			data.put("Kanulumusinavaye","Chakri ");
			data.put("Khadgam","Devi Sri Prasad");
			data.put("Khaleja","Mani Sharma");
			data.put("Killer,Jaanikiraamudu,Sidhartha","Ilayaraja");
			data.put("Kokila","Ilayaraja");
			data.put("Konchem Ishtam Konchem Kashtam","Shankar Ehsaan Loy");
			data.put("Kondaveeti Donga","Ilayaraja");
			data.put("Kotha Bangaram Lokam","Mickey J Meyer");
			data.put("Kotta Bangaru Lokam (2008)","Mickey J Meyer");
			data.put("Kshana Kshanam","M M Keeravani");
			data.put("Kubusam","Vandemataram Srinivas");
			data.put("Kudirithey Kappu Coffee","Yogeshwara Sharma");
			data.put("Kurraloi Kurrolu","Yuan Shankar Raja");
			data.put("Kushi","Mani Sharma");
			data.put("Lahiri Lahiri Lahiri Loo","M M Keeravani");
			data.put("Laila College","");
			data.put("Lankeshvarudu","Raj Koti");
			data.put("Maa Annayya","S A Rajkumar");
			data.put("Maa Baapu Bommaku Pellanta","");
			data.put("Maatarani Mounamidhi","");
			data.put("Madhumasam","");
			data.put("Magadheera","M M Keeravani");
			data.put("Mallanna (2009)","Devi Sri Prasad");
			data.put("Manchi Donga","");
			data.put("Mantra","Anand");
			data.put("Marana Mridagam","Ilayaraja");
			data.put("Maro Charitra","Ilayaraja");
			data.put("Merupu Kalalu","A R Rahman");
			data.put("mIX","");
			data.put("Money","Sri Murthy");
			data.put("Mr.Perfect","Devi Sri Prasad");
			data.put("Murari","Mani Sharma");
			data.put("Murari, Rajakumarudu","Mani Sharma");
			data.put("Nuvvatanate Nenoddantana","Devi Sri Prasad");
			data.put("Nuvve Kavali","Koti");
			data.put("Nuvve.. Nuvve","koti");
			data.put("Nuvvostanantey Nenoddantana","Devi Sri Prasad");
			data.put("Nuvvu Nenu","R P Patnaik");
			data.put("Nuvvu Nenu Prema","A R Rahman");
			data.put("Okariki Okaru","M M Keeravani");
			data.put("Oke okkadu","A R Rahman");
			data.put("OKKADU","Mani Sharma");
			data.put("Orange","Harris Jayaraj");
			data.put("Oy !","Yuan Shankar Raja");
			data.put("Padaharella Vayassu","K Chakravarthi");
			data.put("Pokiri","Mani Sharma");
			data.put("Prema","Ilayaraja");
			data.put("Premadesam","A R Rahman");
			data.put("Premikudu","A R Rahman");
			data.put("Premikula Roju","A R Rahman");
			data.put("Ragavan","Harris Jayaraj");
			data.put("Rakshakudu","A R Rahman");
			data.put("Robo(2010)","A R Rahman");
			data.put("Roja","A R Rahman");
			data.put("Sagara Sangamam","Ilayaraja");
			data.put("Sakhi","A R Rahman");
			data.put("Santhosham","R P Patnaik");
			data.put("Seethakoka Chilaka","Ilayaraja");
			data.put("Seetharamayya Gari Manavaralu","M M Keeravani");
			data.put("Shivaji","A R Rahman");
			data.put("Shivaputrudu","Ilayaraja");
			data.put("Sirivennela","K V Mahadevan");
			data.put("Sri Ramadaasu","M M Keeravani");
			data.put("Subha Sankalpam","M M Keeravani");
			data.put("Suneetha Hits","Suneetha");
			data.put("Surya son of Krishnan (2008)","Harris Jayaraj");
			data.put("Swarna Kamalam","Ilayaraja");
			data.put("Swathi Kiranam","K V Mahadevan");
			data.put("Swathi Muthyam","Ilayaraja");
			data.put("Takkari Donga","Mani Sharma");
			data.put("Vaali","Deva");
			data.put("Vaasu","Harris Jayaraj");
			data.put("Varsham","Devi Sri Prasad");
			data.put("Vennela","Mahesh Shankar");
			data.put("Yamadonga","M M Keeravani");
			data.put("Ye Maya Chesave","A R Rahman");
			data.put("Yuva","A R Rahman");
			
	}
//*/
	}
