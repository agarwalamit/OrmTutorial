package com.example.ormtutorial;

import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class MainActivity extends Activity {

	private final static String DATABASE_URL = "jdbc:h2:mem:account";
	ConnectionSource connectionSource ;
	Dao<Person, String> personDao;



	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try{

			createDB();

			personDao = BaseDaoImpl.createDao(connectionSource, Person.class);

			//create object to be persisted
			Person person = new Person("amit");

			//persist in DB
			personDao.create(person);

			//prepare the query
			QueryBuilder<Person, String> qbBuilder = personDao.queryBuilder();
			qbBuilder.where().eq(Person.FIELD_NAME, "amit");
			PreparedQuery<Person> preparedQuery = qbBuilder.prepare();
			
			//do the query
			List<Person> personList = personDao.query(preparedQuery);

			//see output
			Log.d("output",personList.get(0).getName());
			
			connectionSource.close();
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}
	public void createDB(){
		try{
			if(connectionSource != null){
				Log.d("output", "db exists");
				return;
			}

			connectionSource = new AndroidConnectionSource(new SQLiteOpenHelper(this,DATABASE_URL+"l" , null , 1) {

				@Override
				public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
					// TODO Auto-generated method stub					
				}

				@Override
				public void onCreate(SQLiteDatabase db) {
					// TODO Auto-generated method stub

				}
			});			
			TableUtils.createTable(connectionSource, Person.class);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
