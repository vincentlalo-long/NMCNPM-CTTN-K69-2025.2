import { axiosInstance } from "../axiosInstance";

const USER_API_PREFIX = "/user";

export interface BookingRequest {
  date: string; // dd/mm/yyyy
  slots: { court: string; slot: string }[];
}

export interface BookingResponse {
  success: boolean;
  message?: string;
}

export interface BookedSlot {
  court: string;
  slot: string;
}

// Fetch all booked slots for a given date
export async function getBookedSlots(date: string): Promise<BookedSlot[]> {
  const response = await axiosInstance.get<{ slots: BookedSlot[] }>(
    `${USER_API_PREFIX}/booked-slots`,
    { params: { date } }
  );
  return response.data.slots;
}

// Book slots for a user
export async function bookSlots(data: BookingRequest): Promise<BookingResponse> {
  const response = await axiosInstance.post<BookingResponse>(
    `${USER_API_PREFIX}/book`,
    data
  );
  return response.data;
}