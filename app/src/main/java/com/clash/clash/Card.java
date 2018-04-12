package com.clash.clash;

import android.os.*;

public class Card implements Parcelable{

	private String _id;
	private String idName;
	private String rarity;
	private String type;
	private String name;
	private String description;
	private int elixirCost;

	public Card(String _id, String idName, String rarity, String type, String name, String description, int elixirCost){
		this._id = _id;
		this.idName = idName;
		this.rarity = rarity;
		this.type = type;
		this.name = name;
		this.description = description;
		this.elixirCost = elixirCost;
	}

	protected Card(Parcel in) {
        _id = in.readString();
		idName = in.readString();
		rarity = in.readString();
		type = in.readString();
		name = in.readString();
		description = in.readString();
		elixirCost = in.readInt();
	}

	public static final Creator<Card> CREATOR = new Creator<Card>() {
		@Override
		public Card createFromParcel(Parcel in) {
			return new Card(in);
		}

		@Override
		public Card[] newArray(int size) {
			return new Card[size];
		}
	};

    public String getId(){ return this._id; }
    public void setId( String _id ){ this._id = _id; }

	public String getIdName(){ return this.idName; }
	public void setIdName( String idName ){ this.idName = idName; }

	public String getRarity(){ return this.rarity; }
	public void setRarity( String rarity ){ this.rarity = rarity; }

	public String getType(){ return this.type; }
	public void setType( String type ){ this.type = type; }

	public String getName(){ return this.name; }
	public void setName( String name ){ this.name = name; }

	public String getDescription(){ return this.description; }
	public void setDescription( String description ){ this.description = description; }

	public int getElixirCost(){ return this.elixirCost; }
	public void setElixirCost( int elixirCost ){ this.elixirCost = elixirCost; }

	public String toString(){
		return "";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
		dest.writeString(idName);
		dest.writeString(rarity);
		dest.writeString(type);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeInt(elixirCost);
	}
}