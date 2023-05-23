package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.getConnection;

public class updateEmployeeInfo {

    // 수정할 사원 번호를 입력받고
    // 사원 정보(전화번호, 이메일, 부서코드, 급여, 보너스)를 입력받아 DTO객체에 담아서 update
    // update 성공하면 "직원 정보 수정에 성공하였습니다." 출력
    // update 실패하면 "직원 정보 수정에 실패하였습니다." 출력
    public static void main(String[] args) {
        Connection con = getConnection();
        PreparedStatement pstmt = null;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        int result = 0;
        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));
            String query = prop.getProperty("updateEmployee");

            Scanner sc = new Scanner(System.in);
            System.out.print("수정할 사원 번호를 입력해주세요 : ");
            String empID = sc.nextLine();
            System.out.print("사원의 전화번호를 수정해주세요 : ");
            String empPhone = sc.nextLine().trim();
            System.out.print("사원의 이메일을 수정해주세요 : ");
            String empEmail = sc.nextLine();
            System.out.print("사원의 부서코드를 수정해주세요 : ");
            String empDeptCode = sc.nextLine();
            System.out.print("사원의 급여를 수정해주세요 : ");
            int empSalary = sc.nextInt();
            sc.nextLine();
            System.out.print("사원의 보너스를 수정해주세요 : ");
            double empBonus = sc.nextDouble();
            sc.nextLine();

            employeeDTO.setEmpId(empID);
            employeeDTO.setPhone(empPhone);
            employeeDTO.setEmail(empEmail);
            employeeDTO.setDeptCode(empDeptCode);
            employeeDTO.setSalary(empSalary);
            employeeDTO.setBonus(empBonus);

            // 사원 정보(전화번호, 이메일, 부서코드, 급여, 보너스)를 입력받아 DTO객체에 담아서 update

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, employeeDTO.getPhone());
            pstmt.setString(2, employeeDTO.getEmail());
            pstmt.setString(3, employeeDTO.getDeptCode());
            pstmt.setInt(4, employeeDTO.getSalary());
            pstmt.setDouble(5, employeeDTO.getBonus());
            pstmt.setString(6, employeeDTO.getEmpId());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result > 0) {
            System.out.println("직원 정보 수정에 성공하였습니다.");
            System.out.println("result = " + result);
        } else {
            System.out.println("직원 정보 수정에 실패하였습니다.");
            System.out.println("result = " + result);
        }
    }
}
