package SEP4Data.air4you;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import SEP4Data.air4you.room.Room;
import SEP4Data.air4you.room.RoomRepository;
import SEP4Data.air4you.room.RoomService;
import SEP4Data.air4you.room.RoomServiceImpl;

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
    public void testSaveRoom(){
        Room room = new Room("roomId4","userId4","roomName4", LocalDateTime.now());

        roomService.registerRoom(room);

        verify(dao, times(1)).save(room);
    }

}
