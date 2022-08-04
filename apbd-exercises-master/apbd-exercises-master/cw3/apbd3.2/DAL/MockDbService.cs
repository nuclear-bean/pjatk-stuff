using System.Collections.Generic;
using Cw3.DAL;
using Cw3.Models;

public class MockDbService : IDbService
{
    private static IEnumerable<Student> _students;

    static MockDbService()
    {
        _students = new List<Student>
        {
            new Student{IdStudent=1, FirstName="Jan", LastName="Kowalski"},
            new Student{IdStudent=2, FirstName="Tomasz", LastName="Paździoch"},
            new Student{IdStudent=3, FirstName="Milena", LastName="Warga"},
            new Student{IdStudent=4, FirstName="Krzysztof", LastName="Adamczak"},
            new Student{IdStudent=5, FirstName="Piotr", LastName="Przykładowy"},
        };
    }

    public IEnumerable<Student> GetStudents()
    {
        return _students;
    }
}