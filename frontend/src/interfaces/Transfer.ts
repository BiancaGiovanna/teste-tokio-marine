export interface Transfer {
  id?: number;
  originAccount: string;
  destinationAccount: string;
  transferAmount: number;
  fee?: number | unknown;
  transferDate: string;
  schedulingDate?: string;
}