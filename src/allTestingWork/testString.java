package allTestingWork;

/**
 *
 * @author AD-Rahat
 */

public class testString 
{
    public static  void main(String args[])
    {
        String id = "150103020109";
        
        String faculty = id.substring(3,4);
        String dept = id.substring(5, 6) ;
        
        System.out.println("faculty : " + faculty + "\ndept = " + dept);
        
        String[][] departmentList = { {"N/A", "N/A", "N/A", "N/A", "N/A" }, {"N/A", "BuA", "N/A", "N/A", "N/A"}, 
            {"N/A", "ENG", "L&J", "N/A","ASS", "N/A"}, {"N/A", "ESC", "CSE", "MAT", "N/A"} };
        
        for(int i=0; i<4; i++)
        {
            for(int j=0; j<4; j++)
            {
                System.out.print(departmentList[i][j]  + "\t");
            }
            System.out.println("");
        }  
        
        
        String year, semester;
        year = id.substring(0,2);
        semester = id.substring(3, 4);
        
        String[] SemesterList = {"N/A", "Spring", "Summer", "Fall"};
        //int year_number = Integer.parseInt(year);
        int semester_number = Integer.parseInt(semester);
        
        //System.out.println(year_number + " " + semester_number);
        
        String session = SemesterList[semester_number] + "'" + year;
        
        System.out.println(session +" ");
        
        //System.out.println("new kahini : " + id.substring(id.length()-1, id.length()));
        
        System.out.println("New kahini :");
        
        id = "0183e1938920485";
        
        if (id.matches("[0-9]+") && id.length() > 2) 
            System.out.println("Number");
        else
            System.out.println("Not a Number");
                
        
        
     
        

// 1202 0201 0003
        
        
    }
    
}
