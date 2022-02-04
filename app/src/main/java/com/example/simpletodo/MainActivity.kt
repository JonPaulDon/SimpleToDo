package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks= mutableListOf<String>();

    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //Remove the item from the list
                listOfTasks.removeAt(position)
                //Notify the adapter that something has changed in our data set
                adapter.notifyDataSetChanged()
                //
                saveItems()
            }

        }


        Log.i("Caren","Line15")
        loadItems()
        //look up the recyclerview in layout
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing in the sample user data
         adapter= TaskItemAdapter(listOfTasks,onLongClickListener)
        //Attach the adapter to the recyclerview to populate items
        recyclerView.adapter=adapter
        //Set a layout manager
        recyclerView.layoutManager= LinearLayoutManager(this)
        /*
        findViewById<Button>(R.id.button).setOnClickListener{
            //code in here will be execute when the user clicks on a button
            Log.i("Caren","User clicked on button")

        }
        */
        val inputTextField = findViewById<EditText>(R.id.editTextTextPersonName)
        //Set up the button and input field so that the user can enter a task and add it to the list
        findViewById<Button>(R.id.button).setOnClickListener(){
            //1.Grab the text that user has inputted into the EditText field @+id/editTextTextPersonName
            val userInputtedTask = inputTextField.text.toString()
            //2.Add the string to our list of tasks
            listOfTasks.add(userInputtedTask)

            //notify the adapter
            adapter.notifyItemInserted(listOfTasks.size-1)
            //3.Reset text field
            inputTextField.setText("")
            saveItems()
        }
        

    }
    //Save that data the user has inputted
        //Every line is going to represent a specific task in our list of tasks

    //Save data by writing and reading from a file

    //create a method to get the file we need
    fun getDataFile(): File {
        return File(filesDir,"data.txt")
    }

    //Load the items by reading every line in our file
    fun loadItems(){
        try{
            listOfTasks=org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }

    }
    //Save items by writing them into our data file
    fun saveItems(){
        try{
            org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }


    }

}