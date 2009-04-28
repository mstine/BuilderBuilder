def bldr = new DtoBuilder()

println bldr.build {
	packageName 'edu.bogusu.registration'
	name 'Student'
	field(name:'id', type:'String')
	field(name:'firstName', type:'String')
	field(name:'lastName', type:'String')
	field(name:'hoursEarned', type:'int')
	field(name:'gpa', type:'float')
}