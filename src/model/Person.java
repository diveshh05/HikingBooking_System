package model;

public abstract class Person {

    private String id;
    private String name;
    private String phone;

    // constructors
    public Person(String personID, String name, String phone){
        this.id = personID;
        setName(name);
        setPhone(phone);
    }

    // encapsulation
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public abstract String getRole();

    // encapsulation: setters
    public void setName(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
            this.name = name.trim();
    }

    public void setPhone(String phone){
        if(phone == null || phone.length() != 8){
            throw new IllegalArgumentException("Phone number must be 8 digits");
        }
        for(int i = 0; i < phone.length(); i++){
            if(!Character.isDigit(phone.charAt(i))){
                throw new IllegalArgumentException("Phone can only contain numbers");
            }
        }
        this.phone = phone;
    }

    @Override
    public String toString(){
        return "ID: " + id + " | Name: " + name + " | Phone: " + phone;
    }

}
