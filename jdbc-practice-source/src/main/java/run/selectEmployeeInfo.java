package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class selectEmployeeInfo {

    // 사원 번호를 입력받아 해당 사원을 조회하고 DTO객체에 담아서 출력
    // 출력 구문은 DTO 객체의 toString() 내용과
    // "[이름]([부서명]) [직급명]님 환영합니다." 로 출력.

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        ResultSet rset = null;

        EmployeeDTO emp = new EmployeeDTO();
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));

            String query = prop.getProperty("selectEmployee");
            pstmt = con.prepareStatement(query);

            Scanner sc = new Scanner(System.in);

            System.out.print("조회할 사원의 번호를 입력하세요 : ");
            emp.setEmpId(sc.nextLine());

            pstmt.setString(1, emp.getEmpId());

            rset = pstmt.executeQuery();

            while (rset.next()){
                emp.setEmpName(rset.getString("EMP_NAME"));
                emp.setEmpNo(rset.getString("EMP_NO"));
                emp.setEmail(rset.getString("EMAIL"));
                emp.setPhone(rset.getString("PHONE"));
                emp.setDeptCode(rset.getString("DEPT_CODE"));
                emp.setJobCode(rset.getString("JOB_CODE"));
                emp.setSalLevel(rset.getString("SAL_LEVEL"));
                emp.setSalary(rset.getInt("SALARY"));
                emp.setBonus(rset.getDouble("BONUS"));
                emp.setManagerId(rset.getString("MANAGER_ID"));
                emp.setHireDate(rset.getDate("HIRE_DATE"));
                emp.setEntDate(rset.getDate("ENT_DATE"));
                emp.setEntYn(rset.getString("ENT_YN"));
            }

            System.out.println("["+emp.getEmpName()+"](["+emp.getDeptCode()+"]) ["+emp.getSalLevel()+"]님 환영합니다.");
            System.out.println(emp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
            close(con);
        }
    }
}