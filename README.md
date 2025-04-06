# Task-Tracker
## 1. OVERVIEW
A Task-Tracker application with CLI inteface.
To run the application, download the "task-tracker.jar file" and launch it with ***"java -jar path/to/file/task-tracker.jar"*** command.
Upon first launch, the tasktracker.json file will be created inside the same folder of the .jar file.
tasktracker.json stores all the tasks in json format. It is read every time the application starts and updated after every operation of add, delete, mark or update.
## 2. TASK ANATOMY
A task is composed of five elements, some generated and managed by the application itself. here is a quick view:
### ID
It's an integer that identifies the task. It is unique and generated by the application. It is used to retrieve a specific task to perform actions such as delete or update.
### Description
A brief string describing the task. It should be provided by the user. In case it's not provided, a default one ("no description provided") is used. Can be updated with the *update* command.
### Status
It's the status of a certain task. Status are fixed and can only be: **to-do; in-progress; done**. Can be changed with the *mark* command
### Created At
creation date and time of a certain task. is generated by the application and cannot be changed or updated.
### Updated At
Last update date and time of a certain task. Is generated and managed by the application.
## 3. COMMANDS
Here is the complete command list of this application with brief explanations and examples.
Some operations require mandatory arguments to execute, while others have optional arguments.
Is mandatory that arguments are provided in the correct order.
Arguments are indicated in square brackets.
### commands:
Shows the commands list.
### list [status]:
Shows the tasks in your Task-Traker.
Status argument is optional.
If a valid status is provided, only the tasks with matching status will be shown.
Else, if no status is provided, all thee tasks will be shown.
Valid status are: **to-do; in-progress; done**

>**Examples:**
>1. list -> shows all tasks
>2. list in-progress -> shows only tasks with status "in progress"
>3. list in progress -> wrong input (invalid status). An error message will be displayed
### add [description]:
Adds a new task to your Task-Tracker.
Description argument is optional.
When providing a descriprion, it must be in double quotes.
If no description is provided the task will be added with the default one "no description provided".
Description can be later changed via "update" command (see "update")

>**Examples:**
>1. add -> task with no description
>2. add "my description" -> task with description
>3. add my description -> wrong input (missing double quotes). no task will be added
### delete [ID]:
Deletes the task with the provided ID.
ID argument is mandatory and must be an integer.
Deleted tasks **cannot be recovered**, be careful.

>**Examples:**
>1. delete 1 -> task with ID "1" will be deleted
>2. delete -> wrong input (missing ID). No task will be deleted and an error message will be shown.
>3. delete one -> wrong input (invalid ID). No task will be deleted and an error message will be shown.
### mark [ID] [status]:
Change the status of the task with the provided ID.
Both ID and status arguments are mandatory argument. ID must be the first argument, while status must be the second one.
ID must be an integer and status must be a valid one.
Valid status are: **to-do; in-progress; done**

>**Examples:**
>1. mark 1 done -> stutus of task with ID "1" will be changed to "done"
>2. mark 1 -> wrong input (missing status). No task will be updated and an error message will be shown.
>3. mark done -> wrong input (missing ID). No task will be updated and an error message will be shown.
>4. mark done 1 -> wrong input (argumens order). No task will be updated and an error message will be shown.
### update [ID] [description]:
Change the description of a task with a new one.
Both ID and description arguments are mandatory argument. ID must be the first argument, while description must be the second one.
ID must be an integer and description must be in double quotes.

>**Examples:**
>1. update 1 "my new description" -> description of task with ID "1" will be changed to "my new description"
>2. update 1 -> wrong input (missing description). No task will be updated and an error message will be shown.
>3. update "my new description" -> wrong input (missing ID). No task will be updated and an error message will be shown.
>4. update "my new description" 1 -> wrong input (argumens order). No task will be updated and an error message will be shown.
### help:
Shows help
### close:
Shutdown the application. Same action can be performed with "exit"
## 4. FINAL NOTES
The whole application is developed in Java language with no external libraries but org.json to manage JSON objects (https://github.com/stleary/JSON-java)
This application is bases on roadmap.sh project (https://roadmap.sh/projects/task-tracker) and developed by Mattia Canopoli

