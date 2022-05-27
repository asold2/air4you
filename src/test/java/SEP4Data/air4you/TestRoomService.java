package SEP4Data.air4you;

import SEP4Data.air4you.room.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TestRoomService{

    @InjectMocks
    RoomService roomService = new RoomServiceImpl();

    @Mock
    RoomRepository dao;

    @Test
    public void testFindAllRooms() {

        List<Room> rooms = new ArrayList<Room>();
        Room roomOne = new Room("roomId1","userId1","roomName1", LocalDateTime.now());
        Room roomTwo = new Room("roomId2","userId2","roomName2", LocalDateTime.now());
        Room roomThree = new Room("roomId3","userId3","roomName3", LocalDateTime.now());

        rooms.add(roomOne);
        rooms.add(roomTwo);
        rooms.add(roomThree);

        when(dao.findAll()).thenReturn(rooms);

        List<Room> roomList = roomService.getAllRooms();

        assertEquals(3, roomList.size());

        verify(dao, times(1)).findAll();
    }

    @Test
    public void testCreateOrSaveRoom(){
        Room room = new Room("roomId4","userId4","roomName4", LocalDateTime.now());

        roomService.registerRoom(room);

        verify(dao, times(1)).save(room);
    }

}
