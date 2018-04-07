package com.cmput301w18t26.taskit;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevingordon on 2018-02-26.
 */

/**
 * Contains methods for creating, editing and deleting tasks.
 * Also contains methods for view the task bids and to bid
 */
public class TaskActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected static final String TYPE = "type";
    protected static final Integer FOR_RETURN_LOCATION = 1;
    protected static final Integer FOR_RETURN_CAMERA_PHOTOS = 2;
    protected static final Integer FOR_RETURN_GALLERY_PHOTOS = 3;
    protected static final Integer MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 4;

    private TaskItData db;
    private Task task;
    private Intent intent;
    private PhotoList photos = new PhotoList();

    private User curruser;
    private String intentState;
    private String intentTaskUUID;

    // UI Elements
    private TextView titleText;
    private TextView dateText;
    private TextView statusText;
    private TextView descriptionText;
    private TextView ownerText;
    private TextView locationText;
    private EditText editTitleText;
    private TextView lowbidText;
    private EditText editDescText;

    private Button markCompleteButton;
    private Button bidButton;
    private Button deleteTaskButton;
    private Button editTaskButton;
    private TextView locationview;
    private Button createTaskButton;
    private Button addLocationButton;
    private Button viewBids;
    private Button addBidButton;
    private Button confirmEdits;
    private Spinner spinner;
    private Button cancelEdits;
    private Button addPhotos;

    /**
     * sets button usage and intent passing
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        intentState = intent.getStringExtra(HomeActivity.TYPE);
        intentTaskUUID = intent.getStringExtra("UUID");

        db = TaskItData.getInstance();
        curruser = db.getCurrentUser();

        setupView();
        setupUIElements();
        setupListeners();
        getFreshData();
        refreshView();

        setTitle(intentState);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        final Button markCompleteButton = (Button) findViewById(R.id.markcomplete);
        intent = getIntent();
            if (db.taskExists(intent.getStringExtra("UUID"))) {
                getTaskDetails();
                if (!"Assigned".equals(db.getTaskStatus(task)) || !task.isOwner(curruser)) {
                    markCompleteButton.setVisibility(View.GONE);
                }else {markCompleteButton.setVisibility(View.VISIBLE);}
            }

    }

    /**
     * When the confirm button is pressed on the new task screen, set the details of the task
     * and adds it to the database
     */
    private void setTaskDetails() {
        titleText = (TextView) findViewById(R.id.update_title);
        descriptionText = (TextView) findViewById(R.id.update_description);

        task.setTitle(titleText.getText().toString());
        task.setDescription(descriptionText.getText().toString());
        task.setDate(new Date());
        task.setStatus("Requested");
        task.setOwner(db.getCurrentUser().getOwner());
        String taskUUID = db.addTask(task);
        for (Photo p: photos.getPhotos()) {
            Log.i("TaskActivity","Photo size before "+Integer.toString(p.getFilesize()));
            p.reduceFilesize();
            Log.i("TaskActivity","Photo size after "+Integer.toString(p.getFilesize()));
            p.setParentTask(taskUUID);
            p.setOwner(db.getCurrentUser());
            db.addPhoto(p);
        }
    }

    /**
     * When a task is clicked on from ListActivity, info about the task is retrieved.
     */
    private void getTaskDetails() {

        titleText = (TextView) findViewById(R.id.tasktitle);
        descriptionText = (TextView) findViewById(R.id.taskdescription);
        statusText = (TextView) findViewById(R.id.taskstatus);
        locationText = (TextView) findViewById(R.id.tasklocation);
        ownerText = (TextView) findViewById(R.id.taskowner);
        dateText = (TextView) findViewById(R.id.taskdate);
        lowbidText = (TextView) findViewById(R.id.tasklowbid);

        titleText.setText(task.getTitle());
        descriptionText.setText(task.getDescription());
        statusText.setText(db.getTaskStatus(task));
        if (task.hasLocation()) {
            locationText.setText(task.locationString());
        } else {
            locationText.setText("Unknown");
        }
        ownerText.setText(task.getOwner());
        double lowestBid = db.getLowestBid(task);
        if (lowestBid == -1) {
            lowbidText.setText("None");
        } else {
            lowbidText.setText(String.format("%.2f", (db.getLowestBid(task))));
        }

        dateText.setText(task.getDateString());
    }

    /**
     * User is the owner of the task, sets the spinner to the task status, and sets the
     * text to its current text
     */
    private void editTaskDetails () {
        String status = db.getTaskStatus(task);

        // Sets the dropdown menu, puts default position as the current task status
        String[] dropdownItems = {"Change Task Status", Task.STATUS_REQUESTED};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, dropdownItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        if (status.equals(Task.STATUS_BIDDED) || status.equals(Task.STATUS_ASSIGNED)) {
            spinner.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.GONE);
        }

        editTitleText.setText(task.getTitle(),TextView.BufferType.EDITABLE);
        editDescText.setText(task.getDescription(),TextView.BufferType.EDITABLE);

    }

    /**
     * sets the task details to what the user modifies them to be on the confirm button press.
     * @param task current task being edited
     * @param title title of the task
     * @param desc editable description
     * @param spinner changeable spinner
     */
    private void modifyDetails (Task task, EditText title,EditText desc, Spinner spinner){
        String newstatus = spinner.getSelectedItem().toString();
        String editedTitle = title.getText().toString();
        String editedDesc = desc.getText().toString();

        task.setTitle(editedTitle);
        task.setDescription(editedDesc);
        task.setStatus(newstatus);

        if (newstatus.equals(Task.STATUS_REQUESTED)) {
            task.deleteAssignee();
        }

        db.updateTask(task);
        finish();
    }

    /**
     * Retrieve data from Activity called by startActivityForResult
     * @param requestCode code provided on call to startActivityForResult
     * @param resultCode  result provided by called activity
     * @param data intent containing result data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOR_RETURN_LOCATION && resultCode == RESULT_OK) {
                Location location = new Location("");
                location.setLatitude(data.getDoubleExtra("latitude",0));
                location.setLongitude(data.getDoubleExtra("longitude",0));
                task.setLocation(location);
        } else if (requestCode == FOR_RETURN_CAMERA_PHOTOS && resultCode == RESULT_OK) {
            if (data != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                Photo p = new Photo();
                p.setPhoto(imageBitmap);
                photos.addPhoto(p);
                Log.i("Bitmap size before", Integer.toString(imageBitmap.getByteCount()));
            }
        } else if (requestCode == FOR_RETURN_GALLERY_PHOTOS && resultCode == RESULT_OK) {
            if (data != null) {
                // TODO credit https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media
                Uri photoUri = data.getData();
                // Do something with the photo based on Uri
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    // Load the selected image into a preview
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("TaskActivity", "Resuming...");
        db.resetNotificationCount();
        db.sync();
        getFreshData();
        refreshView();
    }

    /**
     * depending on the state of the Activity, provide different views
     */
    private void setupView() {
        if (intentState.equals("Edit")) {
            setContentView(R.layout.modify_task);
        } else if (intentState.equals("New Task")) {
            setContentView(R.layout.newtask);
        } else { // View task
            setContentView(R.layout.viewtask);
        }
    }

    /**
     * define UI elements with Views
     */
    private void setupUIElements() {
        addLocationButton = (Button) findViewById(R.id.add_location);
        ownerText = (TextView) findViewById(R.id.taskowner);
        createTaskButton = (Button) findViewById(R.id.createtask);
        markCompleteButton = (Button) findViewById(R.id.markcomplete);
        bidButton = (Button) findViewById(R.id.bidTask);
        deleteTaskButton = (Button) findViewById(R.id.deletetask);
        editTaskButton = (Button) findViewById(R.id.edittask);
        locationview = (TextView) findViewById(R.id.tasklocation);
        addBidButton = (Button) findViewById(R.id.bidTask);
        viewBids = (Button) findViewById(R.id.viewBids);
        editTitleText = (EditText) findViewById(R.id.editTitle);
        editDescText = (EditText) findViewById(R.id.editDescription);
        spinner = (Spinner) findViewById(R.id.spinner);
        confirmEdits = (Button) findViewById(R.id.confirmedit);
        cancelEdits = (Button) findViewById(R.id.cancelmodify);
        addPhotos = (Button) findViewById(R.id.add_photos);

    }

    /**
     * provide instances of OnClickListeners for all of the UI elements
     */
    private void setupListeners() {
        if (addLocationButton != null) {
            Log.d("TaskActivity","addLocationButton found, creating listener");
            addLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    intent.putExtra("calltype", "chooseLocation");
                    startActivityForResult(intent, FOR_RETURN_LOCATION);
                    setResult(RESULT_OK);
                }
            });
        }

        if (createTaskButton != null) {
            Log.d("TaskActivity","createTaskButton found, creating listener");
            createTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    setTaskDetails();
                    finish();
                }
            });
        }

        if (ownerText != null) {
            Log.d("TaskActivity","ownerText found, creating listener");
            ownerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TaskActivity","Clicked on username");
                    Intent profileIntent = new Intent(getApplicationContext(), UserActivity.class);
                    profileIntent.putExtra(TYPE, "Other User");
                    profileIntent.putExtra("User", task.getOwner());
                    startActivity(profileIntent);
                    setResult(RESULT_OK);
                }
            });
        }

        if (locationview != null) {
            Log.d("TaskActivity","locationview found, creating listener");
            locationview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (task.hasLocation()) {
                        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                        intent.putExtra("calltype", "viewTaskLocation");
                        intent.putExtra("UUID", task.getUUID());
                        startActivity(intent);
                        setResult(RESULT_OK);
                    } else {
                        Log.i("TaskActivity", "View location denied, no location available");
                    }

                }
            });
        }

        if (markCompleteButton != null) {
            Log.d("TaskActivity","markCompleteButton found, creating listener");
            markCompleteButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    View promptview = getLayoutInflater().inflate(R.layout.rating_prompt, null);
                    AlertDialog.Builder ratingprompt = new AlertDialog.Builder(TaskActivity.this);

                    ratingprompt.setView(promptview);
                    final AlertDialog dialog = ratingprompt.create();
                    dialog.show();
                    Button submitRating = (Button) promptview.findViewById(R.id.submit);
                    final RatingBar ratingBar = (RatingBar) promptview.findViewById(R.id.rating);
                    ;
                    final EditText userReview = (EditText) promptview.findViewById(R.id.description);

                    final User assignee = db.getUserByUsername(task.getAssignee());

                    submitRating.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            assignee.addRating(ratingBar.getRating());
                            String userReviewText = userReview.getText().toString();
                            if (userReviewText != null && !userReviewText.isEmpty()) {
                                assignee.addRatingDescription(userReviewText);
                            }
                            dialog.dismiss();
                            db.updateUser(assignee);
                        }
                    });

                    task.setStatus("Done");
                    markCompleteButton.setVisibility(View.GONE);
                    getTaskDetails();
                }
            });
        }
        if (editTaskButton != null) {
            Log.d("TaskActivity","editTaskButton found, creating listener");
            editTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent editIntent = new Intent(getApplicationContext(), TaskActivity.class);
                    String UUID = task.getUUID();
                    editIntent.putExtra(TYPE, "Edit");
                    editIntent.putExtra("UUID", UUID);
                    startActivity(editIntent);
                    setResult(RESULT_OK);


                }
            });
        }
        if (deleteTaskButton != null) {
            Log.d("TaskActivity","deleteTaskButton found, creating listener");
            deleteTaskButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    View promptview = getLayoutInflater().inflate(R.layout.final_prompt, null);
                    AlertDialog.Builder bidprompt = new AlertDialog.Builder(TaskActivity.this);
                    Button yesButton = (Button) promptview.findViewById(R.id.yes);
                    Button noButton = (Button) promptview.findViewById(R.id.no);
                    TextView question = (TextView) promptview.findViewById(R.id.question);

                    bidprompt.setView(promptview);
                    question.setText("Are you sure you wish to delete this task?");
                    final AlertDialog dialog = bidprompt.create();
                    dialog.show();

                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.deleteTask(task);
                            finish();
                        }
                    });

                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Log.d("delete", "got to bidlist");
                }
            });
        }

        if (viewBids != null) {
            Log.d("TaskActivity","viewBids found, creating listener");
            viewBids.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Task task = db.getTask(intent.getStringExtra("UUID"));
                    Intent intent = new Intent(TaskActivity.this, BidListActivity.class);
                    String UUID = task.getUUID();
                    intent.putExtra("UUID", UUID);
                    startActivity(intent);
                    getTaskDetails();
                    setResult(RESULT_OK);

                }
            });
        }

        if (addBidButton != null) {
            Log.d("TaskActivity","addBidButton found, creating listener");
            addBidButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(TaskActivity.this, BidActivity.class);
                    String UUID = task.getUUID();
                    intent.putExtra("UUID", UUID);
                    startActivity(intent);
                    setResult(RESULT_OK);
                }

            });
        }
        if (confirmEdits != null) {
            Log.d("TaskActivity", "confirmEdits found, creating listener");
            confirmEdits.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    View promptview = getLayoutInflater().inflate(R.layout.final_prompt, null);
                    AlertDialog.Builder bidprompt = new AlertDialog.Builder(TaskActivity.this);
                    Button yesButton = (Button) promptview.findViewById(R.id.yes);
                    Button noButton = (Button) promptview.findViewById(R.id.no);
                    TextView question = (TextView) promptview.findViewById(R.id.question);

                    bidprompt.setView(promptview);
                    question.setText("Are you sure you wish to edit this task?");
                    final AlertDialog dialog = bidprompt.create();
                    dialog.show();

                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            modifyDetails(task, editTitleText, editDescText, spinner);
                            finish();
                            //                        markCompleteButton.setVisibility(View.GONE);
                        }
                    });

                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });

            if (cancelEdits != null) {
                Log.d("TaskActivity", "cancelEdits found, creating listener");
                cancelEdits.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }

        if (addPhotos != null) {
            Log.d("TaskActivity","addPhotos found, creating listener");
            addPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkPermission()) {
                        selectImage();
                    }
                }
            });
        }
    }

    /**
     *
     */
    private void getFreshData() {
        if (intentState.equals("Edit")) {
            task = db.getTask(intentTaskUUID);
        } else if (intentState.equals("New Task")) {
            task = new Task();
        } else {
            task = db.getTask(intentTaskUUID);
        }
    }

    /**
     *
     */
    private void refreshView() {
        if (intentState.equals("Edit")) {
            editTaskDetails();
        } else if (intentState.equals("New Task")) {

        } else { // View task
            getTaskDetails();

            if (!db.getTaskStatus(task).equals(Task.STATUS_ASSIGNED)
                    || !task.isOwner(curruser)) {
                markCompleteButton.setVisibility(View.GONE);
            }

            if (task.isOwner(curruser)){
                bidButton.setVisibility(View.GONE);
            } else {
                deleteTaskButton.setVisibility(View.GONE);
                editTaskButton.setVisibility(View.GONE);
                markCompleteButton.setVisibility(View.GONE);
            }
        }
    }

    /**
     * check permission for storage access on device. If permission not granted ask for permission
     * @return true/false if permission granted/not granted
     */
    private boolean checkPermission(){
        int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED){
            String[] permissionStrings = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(TaskActivity.this,
                    permissionStrings,
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            return false;

        } else {
            return true;
        }
    }

    /**
     * Display a menu asking whether the user would like to take a photo or choose from gallery
     * adapted from http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
     * //TODO put this acknowledge in wiki
     */
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * start activity for getting photos from camera
     */
    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, FOR_RETURN_CAMERA_PHOTOS);
        }
    }

    /**
     * start activity for getting photos from phone storage
     */
    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, FOR_RETURN_GALLERY_PHOTOS);
    }

    /**
     * Callback method part of ActivityCompat.OnRequestPermissionResultCallback interface
     * reports the result of the request for permissions
     * @param requestCode code to identify the request by
     * @param permissions list of permissions requested
     * @param grantResults list of results corresponding to the permissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addPhotos.performClick();
            }
        }
    }

}
