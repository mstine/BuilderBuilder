class DtoBuilderTest extends GroovyTestCase {
	
	void testBuilder() {
		def bldr = new DtoBuilder()
		
		def result = bldr.build {
			packageName 'edu.bogusu.registration'
			name 'Student'
			field(name:'id', type:'String')
			field(name:'firstName', type:'String')
			field(name:'lastName', type:'String')
			field(name:'hoursEarned', type:'int')
			field(name:'gpa', type:'float')
		}
		
		assertEquals(expectedOutput, result.toString())
	}

String expectedOutput = """package edu.bogusu.registration;

public class StudentDTO {

   private String id;
   private String firstName;
   private String lastName;
   private int hoursEarned;
   private float gpa;

   public static class Builder {

      private String id;
      private String firstName;
      private String lastName;
      private int hoursEarned;
      private float gpa;

      private Builder() {}

      public Builder id(String id) {
         this.id = id;
         return this;
      }

      public Builder firstName(String firstName) {
         this.firstName = firstName;
         return this;
      }

      public Builder lastName(String lastName) {
         this.lastName = lastName;
         return this;
      }

      public Builder hoursEarned(int hoursEarned) {
         this.hoursEarned = hoursEarned;
         return this;
      }

      public Builder gpa(float gpa) {
         this.gpa = gpa;
         return this;
      }

      public StudentDTO instance() {
         return new StudentDTO (
            id,
            firstName,
            lastName,
            hoursEarned,
            gpa
         );
      }
   }

   public static Builder builder() {
      return new Builder();
   }

   private StudentDTO (
      String id,
      String firstName,
      String lastName,
      int hoursEarned,
      float gpa
   ) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.hoursEarned = hoursEarned;
      this.gpa = gpa;
   }

   public String getId() {
      return id;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public int getHoursEarned() {
      return hoursEarned;
   }

   public float getGpa() {
      return gpa;
   }

}\n"""
}