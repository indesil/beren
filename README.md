Beren
========

`Beren` is a Java bean validation generator able to generate validators classes at compilation time!
Beren is still under development phase!      

## How does it works?
You need to add two modules into your project. First `beren-compiler` 
which is responsible for annotation processing and code generation.
Second `beren-core` which contains annotations used by compiler.
During compilation time for every class annotated with `@Validator` 
corresponding java class will be generated according to given definitions.

## Configuration
*Beren is still under **implementation phase and was not uploaded into maven repository.**
During this phase you need to clone repository to have it on your local maven repository.*

Add maven dependencies to your project
```xml
<dependencies>
    <dependency>
        <groupId>io.github.indesil</groupId>
        <artifactId>beren-compiler</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>io.github.indesil</groupId>
        <artifactId>beren-core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```
## Usage
Every example can be found in `examples` module

Add `@Validator` to class which will be interface to be implemented by Beren. It must be either `abstract class or interface`. 
```java
@Validator
public abstract class FellowshipValidator {
}
```
`@Validator` has one parameter `BreakingStrategy` which allows to determine
how validator class will deal with invalid values.
- `THROW_ON_FIRST` Will force to throw `ValidationException` 
with proper violation message as soon as invalid value was found 
- `SUMMARIZE_ALL` will force to collect all violation messages.
 No exception will thrown, instead object of class `ValidationResults` will be returned.
 
By default `THROW_ON_FIRST` is used.
Let's add some validation method.
 
```java
@Validate(nullable = false, value = {
    @Field(name = "heroes", operation = "notEmpty")
})
abstract void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing);
```
`@Validate` annotation values:
- `source` only available value for now is `METHOD_DEFINITION` which means validation is build based on Beren annotations. 
- `nullable` processor will create null check for input value. 
If nullable is set to false(default) violation will be created for 
message `nullableMessage`. If set to true validation will be skipped without violation.
- `nullableMessage` violation template. Default value is 
`%{input} must not be null!` where `%{input}` is name of method parameter
- `value` validation definitions for chosen fields.

Important notice!
- For `THROW_ON_FIRST` strategy method must return void
- For `SUMMARIZE_ALL` strategy method must return `ValidationResults`
- Only abstract method can be annotated with `@Validate`
- Validated method must have one and only one input parameter.

If you compile project now a java source file will be created 
in you target/generated-sources/annotations directory.
Beren will implement your class with name <YourClassName>ImplBeren_  
```java
public class FellowshipValidatorImplBeren_ extends FellowshipValidator {
    public FellowshipValidatorImplBeren_() {
        super();
    }

    @Override
    void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing) {
        internal_checkFellowshipBeforeLeave(fellowshipOfTheRing);
    }

    private void internal_checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing) {
        if (fellowshipOfTheRing == null) {
            throw new ValidationException("fellowshipOfTheRing must not be null!");
        }
        if (!Operations.notEmpty(fellowshipOfTheRing.getHeroes())) {
            throw new ValidationException("heroes must not be empty!");
        }
    }
}
```
As we can see messages are quite of of our project scope. 
Let's change them, but first a few words about `@Field` annotation used in example
`name = "heroes", operation = "notEmpty"`

For now there is only single field we want to validate and it's defined by its name.
Beren will try to find this field by getter in this case `getHeroes()`.
For this field we want to perform `notEmpty` operation. 
For your convenience every operation you use must check if field is valid, beren will negate value for you.
And therefore in generated code 
```java
if (!Operations.notEmpty(fellowshipOfTheRing.getHeroes()))
```   
You may now ask - Hey! Where did that `Operations.notEmpty()` came from!?
There are three sources of validation method:
- Method annotated with `@Validate`
- Method already implemented in validator class
- Method defined in beren-configuration.yaml

First two will be discussed later, for now let's focus on configuration file.
In `beren-compiler` module there is a file [beren-default-configuration.yaml](beren-compiler/src/main/resources/beren-default-configuration.yaml)
containing default operation mappings.
```yaml
between(a,b):
    operationCall: io.github.indesil.beren.operations.Operations.between(this, a, b)
    defaultMessage: "%{paramName} must be between %{a} and %{b}"
notEmpty:
    operationCall: io.github.indesil.beren.operations.Operations.notEmpty(this)
    defaultMessage: "%{paramName} must not be empty!"
```       
Important notice:
- Operation class call must be any static method on class path that returns boolean.
For example if you are using apache commons `notEmpty` 
entry may look like `org.apache.commons.lang3.StringUtils.isNotEmpty(this)`
But in this case you wont be able to use `notEmpty` operation 
for collections since `StringUtils` does not have `isNotEmpty(Collection)` method!   
- Operation key must be unique by its name, therefore arguments overloading is not allowed.
- Operation arguments can be used as place holders in message as shown above.
- `this` argument indicate object that is being validated for example `fellowshipOfTheRing.getHeroes()`. 
Definition must contains `this` otherwise it's pointless.
- Single operation key may refer to multiple overloaded methods 
but there is no type validation. For example `Operations.notEmpty(this)` might be pointing at
`Operations.notEmpty(String)` and `Operations.notEmpty(Collection)`.
It will be determined by java compiler during source compilation.

Let's replace nullable message and notEmpty message to fit our project.
```java
@Validate(nullable = false,
    nullableMessage = "Fellowship was not assembled! Is it all lost already?",
    value = {
    @Field(name = "heroes", operation = "notEmpty", message = "The ring must be destroyed! So... anyone volunteer?")
})
abstract void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing);
``` 
`@Field(message)` always overrides default message configured for operation. 
Another way to change `notEmpty` default message is to create
`beren-configuration.yaml` in `resources` folder and then add entry   
```yaml
operationsMappings:
  notEmpty:
    operationCall: Operations.notEmpty(this)
    defaultMessage: "This %{paramName} should not be empty! Where is it!? Where are you my precious!?"
```     
Both `operationCall` and `defaultMessage` must be given! Compilation output may looks like this
```java
if(fellowshipOfTheRing == null) {
  throw new ValidationException("Fellowship was not assembled! Is it all lost already?");
}
// with configuration override
if(!Operations.notEmpty(fellowshipOfTheRing.getHeroes())) {
  throw new ValidationException("This heroes should not be empty! Where is it!? Where are you my precious!?");
}
``` 
or with message override
``` java
if(!Operations.notEmpty(fellowshipOfTheRing.getHeroes())) {
  throw new ValidationException("The ring must be destroyed! So... anyone volunteer?");
}
```
But what if you want to check each of heros in the company?
There are two built in iteration command:
 - `#forEachKey(operation)` iterates over `Map.keySet()` and applies `operation` on each of value
 - `#forEachValue(operation)` iterates over `Map.values()` or `Collection` 
 and applies `operation` on each of value
 - Neither of iteration operation will check for null values! 
 It's is only guarantee that wrapper will replace null with empty collection `CollectionUtils.collectionOrEmpty()`.
 Therefore if you want violation message for null collections you must add one by yourself!
 - `operation` may be `@Validate` method reference, our method that returns 
 boolean or any of configured operations that fit.
   
Let's add validation for each hero.
```java
@Validate(value = {
    @Field(names = {"name", "homeland", "weapon"}, operation = "notNull", message = "Have you forgotten something like %{paramName}?"),
    @Field(type = Integer.class, operation = "greaterThan(0)"),
    @Field(name = "age", operation = "greaterThan(18)", message = "You must be adult to go for adventure!"),
    @Field(name = "race", operation = "spiesCheck", message = "There is a Sauron spy in our team!"),
})
abstract void checkMember(Hero hero);
```
Important notice:
- `names` is list of fields names that behave same as `name`
 but without need of defining `@Field` for each of them.
- `type` Allows to apply `operation` for every field that is assignable to given Class.
It means that for Integer.class every field of type int.class 
and Integer.class will be validated with operation `greaterThan(0)`. Please make notice that 
if you use `Object.class` every field will apply since everything can be assigned to Object type!
- If multiple `@Field` definitions applies for one field then multiple validations 
will be generated for that field! Take a look at generated code for
`type=Integer.class` and `name=age` which is also Integer.
```java
if(!Operations.greaterThan(hero.getAge(), 0)) {
  throw new ValidationException("age must be greater than 0");
}
if(!Operations.greaterThan(hero.getHeight(), 0)) {
  throw new ValidationException("height must be greater than 0");
}
if(!Operations.greaterThan(hero.getWeight(), 0)) {
  throw new ValidationException("weight must be greater than 0");
}
if(!Operations.greaterThan(hero.getAge(), 18)) {
  throw new ValidationException("You must be adult to go for adventure!");
}
```
 In current version Beren cannot support Enums directly. You will need to add you validation method for them 
 ```java
@Id("spiesCheck")
boolean thisMethodIsRacist(Race race) {
    return !GOBLIN.equals(race) && !ORC.equals(race);
}
```
`@Id("spiesCheck")` allows to create operation reference different than method name

Let's finish our validator by adding all of the rest validation definitions

```java
@Validate(nullable = false,
    nullableMessage = "Fellowship was not assembled! Is it all lost already?",
    value = {
    @Field(name = "heroes", operation = "notEmpty"),
    @Field(name = "heroes", operation = "#forEachValue(checkMember)"),
    @Field(name = "heroesStuff", operation = "#forEachKey(neitherOf(['Saruman', 'Balrog', 'Gollum']))", message = "Hey! Where did these things come from???"),
    @Field(name = "heroesStuff", operation = "#forEachValue(isThisGoodStuff)", message = "I think we need to check our supplies before leaving..."),
    @Field(name = "elrondAdvices", operation = "whatDidElrondSaid")
})
abstract void checkFellowshipBeforeLeave(FellowshipOfTheRing fellowshipOfTheRing);

boolean isThisGoodStuff(Inventory inventory) {
    return inventory != null && inventory.getValue() > 0.0 && inventory.getName() != null;
}

@Validate(value = {
    @Field(names = {"name", "homeland", "weapon"}, operation = "notNull", message = "Have you forgotten something like %{paramName}?"),
    @Field(type = Integer.class, operation = "greaterThan(0)"),
    @Field(name = "age", operation = "greaterThan(18)", message = "You must be adult to go for adventure!"),
    @Field(name = "race", operation = "spiesCheck", message = "There is a Sauron spy in our team!"),
})
abstract void checkMember(Hero hero);

@Id("spiesCheck")
boolean thisMethodIsRacist(Race race) {
    return !GOBLIN.equals(race) && !ORC.equals(race);
}

@Validate(value = {
    @Field(pattern = "advice.*", operation = "notNull", message = "Wait! I forgot %{paramName}")
})
abstract void whatDidElrondSaid(VeryImportElrondAdvices advices);
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

## Acknowledgments
 
* Hat tip to [JavaPoet](https://github.com/square/javapoet)
* Inspired by [Selma](http://www.selma-java.org/)
* Special thanks to [JetBrains](https://www.jetbrains.com/?from=BEREN) for giving us open source Intellij license! 
![Jetbrains logo](logo/jetbrains/jetbrainslogo.png "Jetbrains logo")