/****************************************************
*	Checks the form to update a user's info on the
*		ModifyUser.ftl page.						*
*													*
*													*
****************************************************/
function checkModifyUser()
{
	//alert("HI");
	if( document.updateUser.user_name.value.length < 4)
	{
		alert("Name not long enough");
		return false;
	}
	if( document.updateUser.user_title.value.length < 4 )
	{
		alert("User title not long enough")
		return false;
	}
	if( document.updateUser.user_address.value.length < 4 )
	{
		alert("Address is not long enough")
		return false;
	}
	if( document.updateUser.auth_pass.value.length < 4 )
	{
		alert("User password is not long enough")
		return false;
	}

	return true;
}

/***************************************************
*    Check the form to update a department         *
*    in ModifyDepartment.tfl                       *
*                                                  *
***************************************************/
function checkModifyDept()
{
	if( document.updateDept.dept_name.value.length < 4 )
	{
		alert( "Department name is not long enough" );
		return false;
	}

	return true;		
}

/***************************************************
*    Check the form to create a course in          *
*    	CreateCourse.tfl                           *
*                                                  *
***************************************************/
function checkCreateCourse()
{
	if( document.createCourse.course_name.value.length < 4 )
	{
		alert( "The Course name is too short." );
		return false;
	}
	if( document.createCourse.course_desc.value.length < 4 )
	{
		alert( "The description is too short." );
		return false;
	}

	return true;
}
