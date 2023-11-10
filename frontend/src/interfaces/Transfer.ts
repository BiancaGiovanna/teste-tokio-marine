export interface Transfer {
  id: number;
  originAccount: string;
  destinationAccount: string;
  transferAmount: number;
  fee: number;
  transferDate: string;
  schedulingDate: string;
}