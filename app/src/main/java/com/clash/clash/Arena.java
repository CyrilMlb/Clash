package com.clash.clash;

import android.os.*;
import java.util.*;

public class Arena implements Parcelable{

	private String idName;
	private String name;
	private int victoryGold;
	private int minTrophies;
	private ArrayList<Card> cardUnlocks;

	public Arena(String idName, String name, int victoryGold, int minTrophies, ArrayList<Card> cardUnlocks){
		this.idName = idName;
		this.name = name;
		this.victoryGold = victoryGold;
		this.minTrophies = minTrophies;
		this.cardUnlocks = cardUnlocks;
	}

	protected Arena(Parcel in) {
		idName = in.readString();
		name = in.readString();
		victoryGold = in.readInt();
		minTrophies = in.readInt();
		cardUnlocks = in.createTypedArrayList(Card.CREATOR);
	}

	public static final Creator<Arena> CREATOR = new Creator<Arena>() {
		@Override
		public Arena createFromParcel(Parcel in) {
			return new Arena(in);
		}

		@Override
		public Arena[] newArray(int size) {
			return new Arena[size];
		}
	};

	public String getIdName()           { return this.idName; }
	public void   setIdName( String idName ){ this.idName = idName;   }

	public String getName()             { return this.name; }
	public void   setName( String name ){ this.name = name; }

	public int  getVictoryGold()                 { return this.victoryGold;        }
	public void setVictoryGold( int victoryGold ){ this.victoryGold = victoryGold; }

	public int  getMinTrophies()                 { return this.minTrophies;        }
	public void setMinTrophies( int minTrophies ){ this.minTrophies = minTrophies; }

	public ArrayList<Card>   getCardUnlocks()                    { return this.cardUnlocks;        }
	public void     setCardUnlocks( ArrayList<Card> cardUnlocks ){ this.cardUnlocks = cardUnlocks; }

	public String toString(){
		return "";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(idName);
		dest.writeString(name);
		dest.writeInt(victoryGold);
		dest.writeInt(minTrophies);
		dest.writeTypedList(cardUnlocks);
	}
}